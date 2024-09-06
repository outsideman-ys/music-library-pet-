let lastScrollTop = 0;
let timer;

document.addEventListener('scroll', function() {
    const header = document.querySelector('.forehead');
    const currentScroll = window.pageYOffset || document.documentElement.scrollTop;

    clearTimeout(timer);

    if (currentScroll > lastScrollTop) {
        header.classList.add('hidden');
    } else {
        timer = setTimeout(() => {
            header.classList.remove('hidden');
        }, 15);
    }

    if (currentScroll > 0) {
        header.classList.add('scrolled');
    } else {
        header.classList.remove('scrolled');
    }

    lastScrollTop = currentScroll <= 0 ? 0 : currentScroll;
});
