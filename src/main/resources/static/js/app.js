let menu = [];
let cart = [];
let activeCategory = "All";

const money = value => `₹${Number(value).toFixed(2)}`;

document.addEventListener("DOMContentLoaded", loadMenu);

async function loadMenu() {
    try {
        const response = await fetch("/api/menu");
        if (!response.ok) throw new Error("Unable to load menu");
        menu = await response.json();
        buildCategories();
        renderMenu(menu);
    } catch (error) {
        document.getElementById("menuGrid").innerHTML =
            `<div class="loading">Could not load menu. Check whether the backend is running.</div>`;
    }
}

function buildCategories() {
    const categories = ["All", ...new Set(menu.map(item => item.category))];
    document.getElementById("categories").innerHTML = categories.map(category =>
        `<button class="category-button ${category === "All" ? "active" : ""}"
                 onclick="selectCategory('${escapeHtml(category)}', this)">${escapeHtml(category)}</button>`
    ).join("");
}

function selectCategory(category, button) {
    activeCategory = category;
    document.querySelectorAll(".category-button").forEach(b => b.classList.remove("active"));
    button.classList.add("active");
    filterMenu();
}

function filterMenu() {
    const search = document.getElementById("searchBox").value.toLowerCase().trim();

    let result = menu.filter(item => {
        const categoryMatch = activeCategory === "All" || item.category === activeCategory;
        const searchMatch =
            item.name.toLowerCase().includes(search) ||
            item.description.toLowerCase().includes(search);
        return categoryMatch && searchMatch;
    });

    renderMenu(result);
}

function renderMenu(items) {
    const grid = document.getElementById("menuGrid");

    if (!items.length) {
        grid.innerHTML = `<div class="loading">No food items found.</div>`;
        return;
    }

    grid.innerHTML = items.map(item => `
        <article class="food-card">
            <img class="food-image" src="${escapeHtml(item.imageUrl)}"
                 alt="${escapeHtml(item.name)}"
                 onerror="this.src='https://placehold.co/800x500?text=Food'">
            <div class="food-body">
                <div class="food-category">${escapeHtml(item.category)}</div>
                <h3 class="food-name">${escapeHtml(item.name)}</h3>
                <div class="food-description">${escapeHtml(item.description)}</div>
                <div class="food-bottom">
                    <span class="price">${money(item.price)}</span>
                    <button class="add-button" onclick="addToCart(${item.id})">Add +</button>
                </div>
            </div>
        </article>
    `).join("");
}

function addToCart(id) {
    const existing = cart.find(item => item.id === id);

    if (existing) {
        existing.quantity++;
    } else {
        const product = menu.find(item => item.id === id);
        cart.push({...product, quantity: 1});
    }

    renderCart();
    showCartCount();
}

function changeQuantity(id, amount) {
    const item = cart.find(i => i.id === id);
    if (!item) return;

    item.quantity += amount;

    if (item.quantity <= 0) {
        cart = cart.filter(i => i.id !== id);
    }

    renderCart();
    showCartCount();
}

function removeFromCart(id) {
    cart = cart.filter(i => i.id !== id);
    renderCart();
    showCartCount();
}

function cartTotal() {
    return cart.reduce((sum, item) => sum + Number(item.price) * item.quantity, 0);
}

function renderCart() {
    const container = document.getElementById("cartItems");

    if (!cart.length) {
        container.innerHTML = `<div class="loading">Your cart is empty.</div>`;
    } else {
        container.innerHTML = cart.map(item => `
            <div class="cart-item">
                <img src="${escapeHtml(item.imageUrl)}" alt="${escapeHtml(item.name)}">
                <div>
                    <div class="cart-item-name">${escapeHtml(item.name)}</div>
                    <div class="cart-item-price">${money(item.price)} each</div>
                    <div class="qty">
                        <button onclick="changeQuantity(${item.id}, -1)">−</button>
                        <strong>${item.quantity}</strong>
                        <button onclick="changeQuantity(${item.id}, 1)">+</button>
                        <button class="remove" onclick="removeFromCart(${item.id})">Remove</button>
                    </div>
                </div>
                <strong>${money(Number(item.price) * item.quantity)}</strong>
            </div>
        `).join("");
    }

    document.getElementById("cartTotal").textContent = money(cartTotal());
    document.getElementById("checkoutTotal").textContent = money(cartTotal());
}

function showCartCount() {
    document.getElementById("cartCount").textContent =
        cart.reduce((sum, item) => sum + item.quantity, 0);
}

function openCart() {
    renderCart();
    document.getElementById("cartOverlay").classList.remove("hidden");
}

function closeCart() {
    document.getElementById("cartOverlay").classList.add("hidden");
}

function openCheckout() {
    if (!cart.length) {
        alert("Add at least one item to your cart.");
        return;
    }
    closeCart();
    renderCart();
    document.getElementById("checkoutOverlay").classList.remove("hidden");
}

function closeCheckout() {
    document.getElementById("checkoutOverlay").classList.add("hidden");
}

function closeSuccess() {
    document.getElementById("successOverlay").classList.add("hidden");
}

document.getElementById("checkoutForm").addEventListener("submit", async event => {
    event.preventDefault();

    if (!cart.length) return;

    const message = document.getElementById("checkoutMessage");
    message.textContent = "Placing your order...";

    const payload = {
        customerName: document.getElementById("customerName").value.trim(),
        phone: document.getElementById("phone").value.trim(),
        address: document.getElementById("address").value.trim(),
        paymentMethod: document.getElementById("paymentMethod").value,
        items: cart.map(item => ({
            menuItemId: item.id,
            quantity: item.quantity
        }))
    };

    try {
        const response = await fetch("/api/orders", {
            method: "POST",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify(payload)
        });

        const data = await response.json();

        if (!response.ok) {
            throw new Error(data.message || "Could not place order.");
        }

        cart = [];
        showCartCount();
        renderCart();
        closeCheckout();

        document.getElementById("successText").textContent =
            `Order #${data.id} has been placed successfully. Total: ${money(data.totalAmount)}.`;

        document.getElementById("successOverlay").classList.remove("hidden");
        document.getElementById("checkoutForm").reset();
        message.textContent = "";
    } catch (error) {
        message.textContent = error.message;
    }
});

function escapeHtml(value) {
    return String(value)
        .replaceAll("&", "&amp;")
        .replaceAll("<", "&lt;")
        .replaceAll(">", "&gt;")
        .replaceAll('"', "&quot;")
        .replaceAll("'", "&#039;");
}
