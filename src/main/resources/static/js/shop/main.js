const getProducts = async () => {
	try {
		const response = await fetch('/api/products');
		return await response.json();
	} catch (e) {
		return alert(e);
	}
};

const createProductComponent = (product) => {
	const item = document.createElement('li');
	item.className = 'products__item';
	item.innerHTML = `
    <span products__item-description></span>
    <div className="products__image-container">
        <img src="${product.image}" class="products__item-image"/>
    </div>
    <span className="products__item-price">${product.price}</span>
    `;
	const button = document.createElement('button');
	button.className = 'products__button';
	button.textContent = 'Add to basket';
	button.setAttribute('data-product-id', `${product.id}`);
	button.addEventListener('click', (e) => {
		const productId = e.target.getAttribute('data-product-id');
		handleAddToBasket(productId)
			.then(() => refreshCurrentOfer())
			.catch((error) => console.log(error));
	});
	item.appendChild(button);
	return item;
};

const handleAddToBasket = (productId) => {
	fetch(`/api/basket/add/${productId}`, { method: 'POST' });
};

const refreshCurrentOfer = async () => {
	try {
		const response = await fetch('/api/current-offer');
		return await response.json();
	} catch (e) {
		return alert(e);
	}
};
(() => {
	const productsList = document.querySelector('.products');
	getProducts().then((products) =>
		products.forEach((product) => {
			element = createProductComponent(product);
			productsList.appendChild(element);
		})
	);
})();
