(function () {
    'use strict';

    var API_URL = '/api/chat';
    var sessionId = null;
    var isSending = false;
    var questionsAnswered = parseInt(localStorage.getItem('questionsAnswered') || '47');

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
    var questionsEl = document.getElementById('questionsAnswered');

    if (questionsEl) questionsEl.textContent = questionsAnswered;

    // Chat prompt on scroll
    var promptShown = false;
    window.addEventListener('scroll', function () {
        if (promptShown) return;
        if (window.scrollY > 400 && !localStorage.getItem('promptDismissed') && chatPanel.style.display !== 'flex') {
            chatPrompt.style.display = 'flex';
            promptShown = true;
        }
    }, { passive: true });

    if (chatPromptClose) {
        chatPromptClose.addEventListener('click', function (e) {
            e.stopPropagation();
            chatPrompt.style.display = 'none';
            localStorage.setItem('promptDismissed', '1');
        });
    }
    if (chatPrompt) {
        chatPrompt.addEventListener('click', function () {
            chatPrompt.style.display = 'none';
            localStorage.setItem('promptDismissed', '1');
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
        appendMessage('user', message);
        chatInput.value = '';
        setSending(true);
        await streamResponse(message);
    });

    async function streamResponse(message) {
        var assistantDiv = appendMessage('assistant', '');
        var contentEl = assistantDiv.querySelector('.message-content');
        var fullText = '';
        var firstToken = true;
        showStatus('Understanding your question...');

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
                                if (firstToken) { hideStatus(); firstToken = false; }
                                fullText += data.text;
                                contentEl.innerHTML = formatMarkdown(fullText) + '<span class="cursor">|</span>';
                                scrollToBottom();
                            }
                            if (data.session_id) sessionId = data.session_id;
                            if (data.stage) showStatus(formatStage(data.stage));
                        } catch (err) {}
                    }
                }
            }
            contentEl.innerHTML = formatMarkdown(fullText);
            incrementCounter();
            showFollowUps(message);
        } catch (err) {
            hideStatus();
            if (!fullText) contentEl.textContent = 'Connection lost. Please try again.';
        } finally {
            hideStatus();
            setSending(false);
            scrollToBottom();
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
        scrollToBottom();
    }
    function getFollowUps(q) {
        q = q.toLowerCase();
        if (q.includes('skill') || q.includes('tech')) return ['Tell me about his AI projects', 'What cloud platforms does he use?'];
        if (q.includes('ai') || q.includes('rag')) return ['How does the streaming work?', 'What databases does he use?'];
        if (q.includes('experience') || q.includes('work')) return ['What projects has he built?', 'What are his key achievements?'];
        return ['What are his key skills?', 'Tell me about his projects'];
    }

    function incrementCounter() {
        questionsAnswered++;
        localStorage.setItem('questionsAnswered', questionsAnswered.toString());
        if (questionsEl) questionsEl.textContent = questionsAnswered;
    }

    function formatMarkdown(text) {
        var html = escapeHtml(text);
        html = html.replace(/\*\*(.+?)\*\*/g, '<strong>$1</strong>');
        html = html.replace(/`([^`]+)`/g, '<code>$1</code>');
        html = html.replace(/^### (.+)$/gm, '<h4>$1</h4>');
        html = html.replace(/^## (.+)$/gm, '<h3>$1</h3>');
        html = html.replace(/^- (.+)/gm, '• $1');
        html = html.replace(/\n\n/g, '<br><br>');
        html = html.replace(/\n/g, '<br>');
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
        scrollToBottom();
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
    }, { threshold: 0.2 });
    document.querySelectorAll('.timeline-item').forEach(function (item) {
        timelineObserver.observe(item);
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
            var res = await fetch('/api/dsa/categories/' + category);
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
            var res = await fetch('/api/dsa/code/' + category + '/' + filename);
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
        el.textContent = '';
        var lines = code.split('\n');
        var i = 0;
        function next() {
            if (i >= lines.length) return;
            var span = document.createElement('div');
            span.className = 'code-line';
            span.style.animationDelay = (i * 0.03) + 's';
            span.textContent = lines[i];
            el.appendChild(span);
            i++;
            if (i < lines.length) setTimeout(next, 25);
        }
        next();
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
