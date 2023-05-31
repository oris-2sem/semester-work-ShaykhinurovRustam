const reviewModal = document.getElementById('review-modal');
const reviewButton = document.getElementById('review-button');
const reviewOverlay = document.getElementById('review-overlay');

const cartModal = document.getElementById('cart-modal');
const cartButton = document.getElementById('cart-button');
const cartOverlay = document.getElementById('cart-overlay');

if (reviewButton != null) {
    reviewButton.addEventListener("click", () => {
        reviewModal.classList.remove('hidden')
        reviewModal.classList.add('flex')
    });

    reviewOverlay.addEventListener("click", () => {
        reviewModal.classList.add('hidden')
    });
}

if (cartButton != null) {
    cartButton.addEventListener("click", () => {
        cartModal.classList.remove('hidden')
        cartModal.classList.add('flex')
    });
}

if (cartOverlay != null) {
    cartOverlay.addEventListener("click", () => {
        cartModal.classList.add('hidden')
    })
}

// Toast Notifier
const toast = document.getElementById('toast');
const toastCloseButton = document.getElementById('toastCloseButton');

if (toast != null) {

    toastCloseButton.addEventListener('click', () => {
        toast.classList.add('hidden')
    })

    setTimeout(() => {
        if (!toast.classList.contains('hidden')) {
            toast.classList.add('hidden')
        }
    }, 12500)
}