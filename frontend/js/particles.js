// Connecting dots network background
(function() {
    var canvas = document.getElementById('bgCanvas');
    if (!canvas) return;
    var ctx = canvas.getContext('2d');
    var w, h, particles = [];
    var numParticles = 70;
    var maxDist = 150;

    function resize() {
        w = canvas.width = Math.max(window.innerWidth, document.documentElement.clientWidth, window.screen.width);
        h = canvas.height = Math.max(window.innerHeight, document.documentElement.clientHeight);
        canvas.style.width = w + 'px';
        canvas.style.height = h + 'px';
    }
    resize();
    window.addEventListener('resize', function() {
        resize();
        particles = [];
        for (var i = 0; i < numParticles; i++) particles.push(createParticle());
    }, { passive: true });

    function createParticle() {
        var speed = 0.15 + Math.random() * 0.25;
        var angle = Math.random() * Math.PI * 2;
        return {
            x: Math.random() * w,
            y: Math.random() * h,
            vx: Math.cos(angle) * speed,
            vy: Math.sin(angle) * speed,
            r: Math.random() * 1.5 + 0.5,
            drift: (Math.random() - 0.5) * 0.005
        };
    }

    for (var i = 0; i < numParticles; i++) particles.push(createParticle());

    function draw() {
        ctx.clearRect(0, 0, w, h);

        var isDark = document.documentElement.getAttribute('data-theme') !== 'light';
        var dotColor = isDark ? 'rgba(96,165,250,' : 'rgba(37,99,235,';
        var lineColor = isDark ? 'rgba(59,130,246,' : 'rgba(37,99,235,';

        for (var i = 0; i < particles.length; i++) {
            var p = particles[i];
            p.vx += p.drift;
            p.vy += p.drift;
            var speed = Math.sqrt(p.vx * p.vx + p.vy * p.vy);
            if (speed > 0.4) { p.vx *= 0.95; p.vy *= 0.95; }
            if (Math.random() < 0.01) p.drift = (Math.random() - 0.5) * 0.005;

            p.x += p.vx;
            p.y += p.vy;
            // Wrap around edges instead of bouncing (ensures full coverage)
            if (p.x < -10) p.x = w + 10;
            if (p.x > w + 10) p.x = -10;
            if (p.y < -10) p.y = h + 10;
            if (p.y > h + 10) p.y = -10;
        }

        for (var i = 0; i < particles.length; i++) {
            for (var j = i + 1; j < particles.length; j++) {
                var dx = particles[i].x - particles[j].x;
                var dy = particles[i].y - particles[j].y;
                var dist = Math.sqrt(dx * dx + dy * dy);
                if (dist < maxDist) {
                    var opacity = (1 - dist / maxDist) * 0.25;
                    ctx.beginPath();
                    ctx.moveTo(particles[i].x, particles[i].y);
                    ctx.lineTo(particles[j].x, particles[j].y);
                    ctx.strokeStyle = lineColor + opacity + ')';
                    ctx.lineWidth = 0.5;
                    ctx.stroke();
                }
            }
        }

        for (var i = 0; i < particles.length; i++) {
            var p = particles[i];
            ctx.beginPath();
            ctx.arc(p.x, p.y, p.r + 2, 0, Math.PI * 2);
            ctx.fillStyle = dotColor + '0.08)';
            ctx.fill();
            ctx.beginPath();
            ctx.arc(p.x, p.y, p.r, 0, Math.PI * 2);
            ctx.fillStyle = dotColor + '0.4)';
            ctx.fill();
        }

        requestAnimationFrame(draw);
    }
    draw();
})();
