function decreaseAmount(index) {
    let form = document.getElementById(`decrease-form-${index}`);
    let cartItemId = form.elements["cartItemId"].value;
    let amountSpan = document.getElementById(`amount-${index}`);
    let cartAmountSpan = document.getElementById("cart-amount");
    let totalPriceSpan = document.getElementById("total-price");
    let exchangedPriceSpan = document.getElementById("exchanged-price");
    let priceSpan = document.getElementById(`price-${index}`);

    let xhr = new XMLHttpRequest();
    xhr.open("GET", "/decrease_amount?cartItemId=" + cartItemId);
    xhr.onload = function() {
        if (xhr.status === 200) {
            let response = JSON.parse(xhr.responseText);
            let newAmount = response.newAmount;
            let newPrice = response.newPrice;
            let newTotalPrice = response.totalPrice;
            let newCartAmount = response.newCartAmount;
            let newExchangedPrice = response.exchangedPrice;

            cartAmountSpan.textContent = newCartAmount.toString();
            amountSpan.textContent = newAmount.toString();
            priceSpan.textContent = newPrice.toString();
            totalPriceSpan.textContent = newTotalPrice.toString();
            exchangedPriceSpan.textContent = newExchangedPrice.toString();

            if (newAmount === 0) {
                console.log(newAmount)
                let row = form.closest("[data-remove-if-zero=true]");
                row.parentNode.removeChild(row);
            }
        }
    };
    xhr.onerror = function() {
        let response = JSON.parse(xhr.responseText);
        console.log(response.message);
    };
    xhr.send();
}

function increaseAmount(index) {
    let form = document.getElementById(`increase-form-${index}`);
    let cartItemId = form.elements["cartItemId"].value;
    let amountSpan = document.getElementById(`amount-${index}`);
    let cartAmountSpan = document.getElementById("cart-amount");
    let totalPriceSpan = document.getElementById("total-price");
    let exchangedPriceSpan = document.getElementById("exchanged-price");
    let priceSpan = document.getElementById(`price-${index}`);

    let xhr = new XMLHttpRequest();
    xhr.open("GET", "/increase_amount?cartItemId=" + cartItemId);
    xhr.onload = function() {
        if (xhr.status === 200) {
            let response = JSON.parse(xhr.responseText);
            let newAmount = response.newAmount;
            let newPrice = response.newPrice;
            let newTotalPrice = response.totalPrice;
            let newCartAmount = response.newCartAmount;
            let newExchangedPrice = response.exchangedPrice;

            cartAmountSpan.textContent = newCartAmount.toString();
            amountSpan.textContent = newAmount.toString();
            priceSpan.textContent = newPrice.toString();
            totalPriceSpan.textContent = newTotalPrice.toString();
            exchangedPriceSpan.textContent = newExchangedPrice.toString();
        }
    };
    xhr.onerror = function() {
        let response = JSON.parse(xhr.response);
        console.log(response.message);
    };

    xhr.send();
}