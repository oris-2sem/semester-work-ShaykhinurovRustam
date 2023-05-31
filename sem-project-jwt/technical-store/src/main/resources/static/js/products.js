const categoryDropdown = document.getElementById('categoryDropdown');
const categoryDropdownButton = document.getElementById('categoryDropdownButton');

const userDropdown = document.getElementById('userDropdown');
const userDropdownButton = document.getElementById('userDropdownButton');

const adminDropdown = document.getElementById('adminDropdown');
const adminDropdownButton = document.getElementById('adminDropdownButton');

categoryDropdownButton.addEventListener('click', () => {
    if (userDropdown != null) userDropdown.classList.remove('active')
    if (adminDropdown != null) adminDropdown.classList.remove('active')
    categoryDropdown.classList.toggle('active')
})

if (userDropdown != null) {
    userDropdownButton.addEventListener('click', () => {
        categoryDropdown.classList.remove('active')
        if (adminDropdown != null) adminDropdown.classList.remove('active')
        userDropdown.classList.toggle('active')
    })
}

if (adminDropdown != null) {
    adminDropdownButton.addEventListener('click', () => {
        categoryDropdown.classList.remove('active')
        if (userDropdown != null) userDropdown.classList.remove('active')
        adminDropdown.classList.toggle('active')
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
    }, 7000)
}