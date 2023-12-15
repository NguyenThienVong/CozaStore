app = angular.module("admin-app", ["ngRoute"]);
app.config(function($routeProvider) {
	$routeProvider
		.when("/order", {
			templateUrl: "/admin/order/index.html",
			controller: "order-ctrl"
		})

		.when("/product", {
			templateUrl: "/admin/product/index.html",
			controller: "product-ctrl"
		})

		.when("/category", {
			templateUrl: "/admin/category/index.html",
			controller: "category-ctrl"
		})

		.when("/blog", {
			templateUrl: "/admin/blog/index.html",
			controller: "blog-ctrl"
		})

		.when("/account", {
			templateUrl: "/admin/account/index.html",
			controller: "account-ctrl"
		})

		.when("/authority", {
			templateUrl: "/admin/authority/index.html",
			controller: "authority-ctrl"
		})

		.when("/voucher", {
			templateUrl: "/admin/voucher/index.html",
			controller: "voucher-ctrl"
		})

		.when("/discount", {
			templateUrl: "/admin/discount/index.html",
			controller: "discount-ctrl"
		})

		.when("/top-product", {
			templateUrl: "/admin/product/topProduct.html",
			controller: "product-ctrl"
		})
		
		.when("/top-customer", {
			templateUrl: "/admin/account/topCustomer.html",
			controller: "account-ctrl"
		})
		
		.when("/top-category", {
			templateUrl: "/admin/category/topCategory.html",
			controller: "category-ctrl"
		})
		
		.when("/revenue-order", {
			templateUrl: "/admin/order/revenueOrder.html",
			controller: "order-ctrl"
		})

		.otherwise({
			templateUrl: "/admin/order/index.html",
			controller: "order-ctrl"
		});
});