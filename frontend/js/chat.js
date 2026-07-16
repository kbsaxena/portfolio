(function () {
    'use strict';

    var API_URL = 'https://api.kbsaxena.in/api/chat';
    var sessionId = null;
    var isSending = false;

    // DOM elements (matching IDs in index.html)
    var chatToggle = document.getElementById('chatToggle');
    var chatPanel = document.getElementById('chatPanel');
    var chatClose = document.getElementById('chatClose');
    var chatForm = document.getElementById('chatForm');
    var chatInput = document.getElementById('chatInput');
    var chatSend = document.getElementById('chatSend');
    var chatMessages = document.getElementById('chatMessages');
    var chatSuggestions = document.getElementById('chatSuggestions');
    var chatStatus = document.getElementById('chatStatus');
    var statusText = document.getElementById('statusText');
    var chatPrompt = document.getElementById('chatPrompt');
    var chatPromptClose = document.getElementById('chatPromptClose');
    var questionsEl = document.getElementById('questionsAsked');
    var visitorsEl = document.getElementById('visitorsCount');

    // Fetch global stats from server
    fetch('https://api.kbsaxena.in/api/stats').then(function(r) { return r.json(); }).then(function(data) {
        if (data.questions_asked !== undefined && questionsEl) questionsEl.textContent = data.questions_asked;
        if (data.visitors !== undefined && visitorsEl) visitorsEl.textContent = data.visitors;
    }).catch(function() {});

    // Chat prompt on scroll
    // Chat prompt — shows after 5 seconds OR on first scroll
    var promptShown = false;
    function showPrompt() {
        if (!promptShown && chatPanel.style.display !== 'flex') {
            chatPrompt.style.display = 'flex';
            promptShown = true;
        }
    }
    setTimeout(showPrompt, 5000);
    window.addEventListener('scroll', function() { showPrompt(); }, { passive: true, once: true });

    if (chatPromptClose) {
        chatPromptClose.addEventListener('click', function (e) {
            e.stopPropagation();
            chatPrompt.style.display = 'none';
        });
    }
    if (chatPrompt) {
        chatPrompt.addEventListener('click', function () {
            chatPrompt.style.display = 'none';
            openChat();
        });
    }

    // Toggle
    chatToggle.addEventListener('click', function (e) {
        e.preventDefault();
        if (chatPanel.style.display === 'flex') {
            chatPanel.style.display = 'none';
        } else {
            openChat();
        }
        if (chatPrompt) chatPrompt.style.display = 'none';
    });

    function openChat() {
        chatPanel.style.display = 'flex';
        chatInput.focus();
    }

    // Hero "Ask AI" link
    var heroAskAI2 = document.getElementById('heroAskAI2');
    if (heroAskAI2) {
        heroAskAI2.addEventListener('click', function(e) {
            e.preventDefault();
            openChat();
        });
    }

    // Project "Ask AI" buttons
    document.querySelectorAll('.project-ask-btn').forEach(function(btn) {
        btn.addEventListener('click', function() {
            var query = btn.getAttribute('data-query');
            openChat();
            chatInput.value = query;
            chatForm.dispatchEvent(new Event('submit'));
        });
    });

    // Stat "Ask AI" clicks
    document.querySelectorAll('.stat-chat').forEach(function(stat) {
        stat.addEventListener('click', function() {
            var query = stat.getAttribute('data-query');
            openChat();
            chatInput.value = query;
            chatForm.dispatchEvent(new Event('submit'));
        });
    });

    chatClose.addEventListener('click', function (e) {
        e.preventDefault();
        e.stopPropagation();
        chatPanel.style.display = 'none';
    });

    // Suggestions
    if (chatSuggestions) {
        chatSuggestions.addEventListener('click', function (e) {
            var btn = e.target.closest('.chat-suggestion');
            if (btn && !isSending) {
                chatInput.value = btn.getAttribute('data-query');
                chatForm.dispatchEvent(new Event('submit'));
            }
        });
    }

    // Submit
    chatForm.addEventListener('submit', async function (e) {
        e.preventDefault();
        var message = chatInput.value.trim();
        if (!message || isSending) return;
        if (chatSuggestions) chatSuggestions.style.display = 'none';
        // Remove previous follow-up suggestions
        document.querySelectorAll('.follow-ups').forEach(function(el) { el.remove(); });
        appendMessage('user', message);
        chatInput.value = '';
        setSending(true);
        await streamResponse(message);
    });

    async function streamResponse(message) {
        showStatus('Understanding your question...');
        var assistantDiv = appendMessage('assistant', '');
        var contentEl = assistantDiv.querySelector('.message-content');
        contentEl.innerHTML = '<span class="typing-dots">●●●</span>';
        var fullText = '';
        var firstToken = true;
        var userScrolled = false;

        // Detect if user manually scrolls during streaming
        var onScroll = function() { userScrolled = true; };
        chatMessages.addEventListener('scroll', onScroll);

        try {
            var response = await fetch(API_URL, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ message: message, session_id: sessionId }),
            });
            if (!response.ok) {
                contentEl.textContent = 'Sorry, something went wrong. Please try again.';
                hideStatus();
                setSending(false);
                return;
            }
            var reader = response.body.getReader();
            var decoder = new TextDecoder();
            var buffer = '';
            while (true) {
                var result = await reader.read();
                if (result.done) break;
                buffer += decoder.decode(result.value, { stream: true });
                var lines = buffer.split('\n');
                buffer = lines.pop() || '';
                for (var i = 0; i < lines.length; i++) {
                    if (lines[i].startsWith('data: ')) {
                        try {
                            var data = JSON.parse(lines[i].slice(6));
                            if (data.text !== undefined) {
                                if (firstToken) {
                                    hideStatus();
                                    firstToken = false;
                                    // Scroll to TOP of assistant message so user reads from start
                                    assistantDiv.scrollIntoView({ behavior: 'instant', block: 'start' });
                                }
                                fullText += data.text;
                                contentEl.innerHTML = formatMarkdown(fullText) + '<span class="cursor">|</span>';
                            }
                            if (data.session_id) sessionId = data.session_id;
                            if (data.stage) showStatus(formatStage(data.stage));
                            if (data.questions_asked && questionsEl) questionsEl.textContent = data.questions_asked;
                        } catch (err) {}
                    }
                }
            }
            contentEl.innerHTML = formatMarkdown(fullText);
            if (fullText) { showFollowUps(message); }
            else { contentEl.textContent = 'No response received. Please try again.'; }
        } catch (err) {
            hideStatus();
            if (!fullText) contentEl.textContent = 'Connection error. Please try again.';
        } finally {
            hideStatus();
            setSending(false);
            chatMessages.removeEventListener('scroll', onScroll);
        }
    }

    function showStatus(text) { if (chatStatus) { chatStatus.style.display = 'flex'; statusText.textContent = text; } }
    function hideStatus() { if (chatStatus) chatStatus.style.display = 'none'; }
    function formatStage(stage) {
        var map = { understanding: 'Understanding your question...', searching: 'Searching knowledge base...', generating: 'Generating response...' };
        return map[stage] || stage;
    }

    function showFollowUps(query) {
        var followUps = getFollowUps(query);
        if (!followUps.length) return;
        var div = document.createElement('div');
        div.className = 'follow-ups';
        div.innerHTML = followUps.map(function (q) { return '<button class="follow-up-btn" data-query="' + q.replace(/"/g, '&quot;') + '">' + q + '</button>'; }).join('');
        div.addEventListener('click', function (e) {
            var btn = e.target.closest('.follow-up-btn');
            if (btn && !isSending) { chatInput.value = btn.getAttribute('data-query'); chatForm.dispatchEvent(new Event('submit')); div.remove(); }
        });
        chatMessages.appendChild(div);
    }
    function getFollowUps(q) {
        q = q.toLowerCase();
        // DSA-specific follow-ups — only trigger on clearly DSA-related queries
        if (q.includes('two sum') || (q.includes('array') && !q.includes('kulbhushan'))) return ['How would you optimize this for sorted arrays?', 'What other array problems has he solved?'];
        if (q.includes('binary search')) return ['When do you use binary search on answer?', 'What is the time complexity of binary search?'];
        if (q.includes('linked list')) return ['How to detect a cycle in linked list?', 'What is the difference between singly and doubly linked list?'];
        if (q.includes('traversal') || (q.includes('tree') && !q.includes('decision'))) return ['What is the difference between BFS and DFS?', 'How to find lowest common ancestor?'];
        if (q.includes('dijkstra') || (q.includes('graph') && !q.includes('kulbhushan'))) return ['How to detect cycles in a directed graph?', 'What is topological sort used for?'];
        if (q.includes('knapsack') || q.includes('fibonacci') || q.includes('dynamic programming')) return ['What is the difference between memoization and tabulation?', 'How to identify if a problem needs DP?'];
        if (q.includes('merge sort') || q.includes('quick sort') || q.includes('sorting algorithm')) return ['Which sorting algorithm is best for large datasets?', 'What is the space complexity of merge sort?'];
        if (q.includes('parenthes') || (q.includes('stack') && q.includes('problem'))) return ['What problems are best solved with stacks?', 'How to implement a queue using stacks?'];
        if (q.includes('heap') || q.includes('priority queue')) return ['When to use min-heap vs max-heap?', 'How to find kth largest element?'];
        if (q.includes('greedy algorithm')) return ['How to know if greedy approach works?', 'What is the difference between greedy and DP?'];
        if (q.includes('backtrack') || q.includes('permut') || q.includes('subset')) return ['What is the time complexity of generating permutations?', 'How is backtracking different from brute force?'];
        if (q.includes('palindrome') || q.includes('anagram')) return ['What string algorithms does he know?', 'How to check if two strings are anagrams?'];
        // Portfolio-specific follow-ups
        if (q.includes('explain') && (q.includes('code') || q.includes('algorithm'))) return ['What is the time complexity of this?', 'What real-world problems use this pattern?'];
        if (q.includes('skill') || q.includes('tech stack')) return ['How does he apply AI in his work?', 'What cloud platforms has he used?'];
        if (q.includes('ai') || q.includes('rag') || q.includes('agent') || q.includes('llm')) return ['How many agents did he build?', 'What is the RAG architecture he uses?'];
        if (q.includes('experience') || q.includes('work') || q.includes('hexagon') || q.includes('epam')) return ['What was his biggest achievement?', 'How long has he worked with Java?'];
        if (q.includes('project') || q.includes('portfolio') || q.includes('dataflow')) return ['How does this AI assistant work?', 'What is the tech stack of this site?'];
        if (q.includes('kubernetes') || q.includes('docker') || q.includes('cloud') || q.includes('aws')) return ['How did he reduce DevOps efforts by 40%?', 'What deployment strategies does he use?'];
        if (q.includes('java') || q.includes('spring') || q.includes('python') || q.includes('fastapi')) return ['Which does he prefer — Java or Python?', 'What frameworks has he used?'];
        return ['Tell me about his AI experience', 'What projects has he built?'];
    }

    function formatMarkdown(text) {
        // Normalize multiple newlines to max 2
        text = text.replace(/\n{3,}/g, '\n\n');
        var html = escapeHtml(text);
        html = html.replace(/\*\*(.+?)\*\*/g, '<strong>$1</strong>');
        html = html.replace(/`([^`]+)`/g, '<code>$1</code>');
        html = html.replace(/^### (.+)$/gm, '<h4>$1</h4>');
        html = html.replace(/^## (.+)$/gm, '<h3>$1</h3>');
        html = html.replace(/^\* (.+)/gm, '<li>$1</li>');
        html = html.replace(/^- (.+)/gm, '<li>$1</li>');
        // Wrap consecutive list items in ul, remove any br between them
        html = html.replace(/(<li>.*?<\/li>\s*)+/g, function(match) {
            return '<ul>' + match.replace(/<br\s*\/?>/g, '').replace(/\n/g, '') + '</ul>';
        });
        // Paragraphs
        html = html.replace(/\n\n/g, '</p><p>');
        html = html.replace(/\n/g, ' ');
        html = '<p>' + html + '</p>';
        html = html.replace(/<p>\s*<\/p>/g, '');
        html = html.replace(/<p>\s*(<h[34]>)/g, '$1');
        html = html.replace(/(<\/h[34]>)\s*<\/p>/g, '$1');
        html = html.replace(/<p>\s*(<ul>)/g, '$1');
        html = html.replace(/(<\/ul>)\s*<\/p>/g, '$1');
        return html;
    }

    function appendMessage(role, text) {
        var div = document.createElement('div');
        div.className = 'message ' + role;
        var p = document.createElement('p');
        p.className = 'message-content';
        if (text) p.innerHTML = formatMarkdown(text);
        div.appendChild(p);
        chatMessages.appendChild(div);
        // Only scroll to bottom for user messages (so user sees their own message)
        if (role === 'user') scrollToBottom();
        return div;
    }

    function scrollToBottom() { chatMessages.scrollTop = chatMessages.scrollHeight; }
    function setSending(s) { isSending = s; chatSend.disabled = s; chatInput.disabled = s; if (s) setTimeout(function () { isSending = false; chatSend.disabled = false; chatInput.disabled = false; }, 2000); }
    function escapeHtml(t) { var d = document.createElement('div'); d.textContent = t; return d.innerHTML; }

    // Accordion
    document.querySelectorAll('.accordion-toggle').forEach(function (btn) {
        btn.addEventListener('click', function () {
            var content = btn.nextElementSibling;
            content.classList.toggle('open');
            var icon = btn.querySelector('.accordion-icon');
            if (icon) icon.textContent = content.classList.contains('open') ? '−' : '+';
        });
    });

    // Timeline scroll animation
    var timelineObserver = new IntersectionObserver(function (entries) {
        entries.forEach(function (entry) {
            if (entry.isIntersecting) {
                entry.target.classList.add('visible');
                timelineObserver.unobserve(entry.target);
            }
        });
    }, { threshold: 0.15 });
    document.querySelectorAll('.timeline-item').forEach(function (item) {
        timelineObserver.observe(item);
    });

    // Timeline line animation
    var timeline = document.querySelector('.timeline');
    if (timeline) {
        var lineObserver = new IntersectionObserver(function (entries) {
            if (entries[0].isIntersecting) {
                timeline.classList.add('line-animated');
                lineObserver.unobserve(timeline);
            }
        }, { threshold: 0.2 });
        lineObserver.observe(timeline);
    }

    // Section scroll animation
    var sectionObserver = new IntersectionObserver(function (entries) {
        entries.forEach(function (entry) {
            if (entry.isIntersecting) {
                entry.target.classList.add('visible');
                sectionObserver.unobserve(entry.target);
            }
        });
    }, { threshold: 0.1 });
    document.querySelectorAll('.section').forEach(function (sec) {
        sectionObserver.observe(sec);
    });

    // Accordion animation
    document.querySelectorAll('.accordion').forEach(function (acc) {
        sectionObserver.observe(acc);
    });

    // Skill cards expand
    document.querySelectorAll('.skill-card').forEach(function (card) {
        card.addEventListener('click', function () {
            var detail = card.querySelector('.skill-detail');
            if (detail) detail.classList.toggle('hidden');
        });
    });

    // DSA
    var dsaGrid = document.getElementById('dsaGrid');
    var dsaProblems = document.getElementById('dsaProblems');
    var dsaProblemList = document.getElementById('dsaProblemList');
    var dsaCategoryTitle = document.getElementById('dsaCategoryTitle');
    var dsaBack = document.getElementById('dsaBack');
    var codePreview = document.getElementById('codePreview');
    var codeTitle = document.getElementById('codeTitle');
    var codeContent = document.getElementById('codeContent');
    var codeClose = document.getElementById('codeClose');
    var codeAskAI = document.getElementById('codeAskAI');
    var currentFileName = '';
    var currentCode = '';

    document.querySelectorAll('.dsa-card').forEach(function (card) {
        card.addEventListener('click', function () {
            var category = card.getAttribute('data-category');
            var title = card.querySelector('h4').textContent;
            loadProblems(category, title);
        });
    });

    async function loadProblems(category, title) {
        dsaCategoryTitle.textContent = title;
        dsaProblemList.innerHTML = '<p>Loading...</p>';
        dsaGrid.style.display = 'none';
        dsaProblems.style.display = 'block';
        codePreview.style.display = 'none';
        try {
            var res = await fetch('https://api.kbsaxena.in/api/dsa/categories/' + category);
            var data = await res.json();
            if (!data.problems || !data.problems.length) { dsaProblemList.innerHTML = '<p>No problems found.</p>'; return; }
            dsaProblemList.innerHTML = '';
            data.problems.forEach(function (p) {
                var btn = document.createElement('button');
                btn.className = 'dsa-problem-btn';
                btn.textContent = p.name;
                btn.addEventListener('click', function () { loadCode(category, p.file, p.name); });
                dsaProblemList.appendChild(btn);
            });
        } catch (e) { dsaProblemList.innerHTML = '<p>Failed to load.</p>'; }
    }

    async function loadCode(category, filename, displayName) {
        codeTitle.textContent = filename;
        codeContent.textContent = 'Loading...';
        codePreview.style.display = 'block';
        currentFileName = displayName;
        try {
            var res = await fetch('https://api.kbsaxena.in/api/dsa/code/' + category + '/' + filename);
            var data = await res.json();
            if (data.code) { currentCode = data.code; typeCode(data.code, codeContent); }
            else codeContent.textContent = 'Failed to load.';
        } catch (e) { codeContent.textContent = 'Failed to load.'; }
        codePreview.scrollIntoView({ behavior: 'smooth', block: 'nearest' });
    }

    if (dsaBack) dsaBack.addEventListener('click', function () { dsaProblems.style.display = 'none'; dsaGrid.style.display = 'grid'; codePreview.style.display = 'none'; });
    if (codeClose) codeClose.addEventListener('click', function () { codePreview.style.display = 'none'; });
    if (codeAskAI) codeAskAI.addEventListener('click', function () { openChat(); chatInput.value = 'Explain the ' + currentFileName + ' algorithm — how it works, time complexity, and space complexity'; chatForm.dispatchEvent(new Event('submit')); });

    function typeCode(code, el) {
        el.innerHTML = '';
        var highlighted = highlightJava(code);
        var lines = highlighted.split('\n');
        var i = 0;
        function next() {
            if (i >= lines.length) return;
            var div = document.createElement('div');
            div.className = 'code-line';
            div.style.animationDelay = (i * 0.02) + 's';
            div.innerHTML = lines[i];
            el.appendChild(div);
            i++;
            if (i < lines.length) setTimeout(next, 20);
        }
        next();
    }

    function highlightJava(code) {
        // Process line by line to handle comments properly
        return code.split('\n').map(function(line) {
            var html = escapeHtml(line);
            // Check if line is a comment first
            var trimmed = html.trim();
            if (trimmed.startsWith('//')) {
                if (/TC|SC|Time|Space|Complexity|O\(/i.test(trimmed)) {
                    return '<span class="cmt-tc">' + html + '</span>';
                }
                return '<span class="cmt">' + html + '</span>';
            }
            // Keywords
            html = html.replace(/\b(public|private|protected|static|void|int|long|double|float|boolean|char|String|class|interface|extends|implements|return|if|else|for|while|do|switch|case|break|continue|new|this|super|try|catch|finally|throw|throws|import|package|final|abstract|synchronized|volatile|transient|null|true|false)\b/g, '<span class="kw">$1</span>');
            // Class names (PascalCase identifiers not already wrapped in a span)
            html = html.replace(/(>|^)([^<]*)/g, function(match, prefix, text) {
                var replaced = text.replace(/\b([A-Z][a-zA-Z0-9]*)\b/g, '<span class="cls">$1</span>');
                return prefix + replaced;
            });
            // Method names (lowercase word before parenthesis)
            html = html.replace(/\b([a-z]\w*)\s*(?=\()/g, '<span class="method">$1</span>');
            // Strings
            html = html.replace(/(&quot;[^&]*?&quot;)/g, '<span class="str">$1</span>');
            // Numbers
            html = html.replace(/\b(\d+)\b/g, '<span class="num">$1</span>');
            // Annotations
            html = html.replace(/(@\w+)/g, '<span class="ann">$1</span>');
            return html;
        }).join('\n');
    }

    // Theme
    var themeToggle = document.getElementById('themeToggle');
    function setTheme(dark) {
        document.documentElement.setAttribute('data-theme', dark ? 'dark' : 'light');
        themeToggle.textContent = dark ? '☀️' : '🌙';
        localStorage.setItem('theme', dark ? 'dark' : 'light');
    }
    var saved = localStorage.getItem('theme');
    if (saved) setTheme(saved === 'dark'); else setTheme(true);
    themeToggle.addEventListener('click', function () {
        setTheme(document.documentElement.getAttribute('data-theme') !== 'dark');
    });

    // Init chat panel hidden
    chatPanel.style.display = 'none';
})();
