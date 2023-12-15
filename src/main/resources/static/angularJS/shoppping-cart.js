const app = angular.module("cozastore", []);
app.controller("ctrl", function($scope, $http, $timeout) {

	$scope.loadSALE = function() {

		var startTimeFromStorage = localStorage.getItem('startTime');

		var endTimeFromStorage = localStorage.getItem('endTime');

		var nowTimestamp = new Date().toISOString();

		if (startTimeFromStorage && endTimeFromStorage) {
			var startTimestamp = new Date(startTimeFromStorage).toISOString();
			var endTimestamp = new Date(endTimeFromStorage).toISOString();

			if (startTimestamp <= nowTimestamp && nowTimestamp <= endTimestamp) {
				console.log('Trong khoảng thời gian hợp lệ');
				document.getElementById("quandz").style.display = 'block';

			} else {
				console.log('Ngoài khoảng thời gian hợp lệ');
				document.getElementById('quandz').style.display = 'none';
			}
		} else {
			console.log('Thời gian không hợp lệ trong Local Storage');
			document.getElementById('quandz').style.display = 'none';
		}

	}

	$timeout(function() {
		$scope.loadSALE();
	});



	$scope.changeRam = function() {

		var priceDisplay = document.getElementById('priceDisplay');
		var oldPriceInput = document.getElementById('oldPrice');
		var oldPrice = oldPriceInput.value;

		function updatePriceDisplay(newPrice) {
			priceDisplay.innerHTML = Number(newPrice).toFixed(0).replace(/\B(?=(\d{3})+(?!\d))/g, ".");
		}

		var selectedRam = $scope.ram;
		console.log(selectedRam);

		switch (selectedRam) {
			case '128G':
				var newPrice = oldPrice;
				break;
			case '256G':
				var newPrice = parseFloat(oldPrice) + 500000;
				break;
			case '512G':
				var newPrice = parseFloat(oldPrice) + 1000000;
				break;
			case '1TB':
				var newPrice = parseFloat(oldPrice) + 2000000;
				break;
			default:
				var newPrice = oldPrice;
				break;
		}
		updatePriceDisplay(newPrice);
	};

	$scope.ram = '128G';
	$scope.color = 'Black';
	$scope.qty = 1;

	$scope.congQuantity = function() {
		$scope.qty++;
	}

	$scope.truQuantity = function() {
		if ($scope.qty > 1) {
			$scope.qty--;
		} else {
			$scope.qty = 1;
		}
	}

	$scope.updateRam = function() {
		var selectedQuantityElement = document.getElementById('ram');
		if (selectedQuantityElement) {
			var selectedQuantity = selectedQuantityElement.value;
			$scope.ram = selectedQuantity || '128G';
			console.log("Quantity Ram Updated: " + $scope.ram);
		} else {
			$scope.ram = '128G'
		}
	}

	$scope.updateColor = function() {
		var selectedQuantityElement = document.getElementById('color');
		if (selectedQuantityElement) {
			var selectedQuantity2 = selectedQuantityElement.value;
			$scope.color = selectedQuantity2 || 'Black';
			console.log("Quantity color Updated: " + $scope.color);
		} else {
			$scope.color = 'Black'
		}
	}

	$scope.vouchers = [];

	$scope.voucher = function() {
		var username = $("#username").text();
		$http.get(`/rest/account-voucher/${username}`).then(resp => {
			$scope.vouchers = resp.data;
		});
	}


	$scope.voucher();
	$scope.voucherCost = null;
	$scope.selectedVoucher = {};
	$scope.voucherID = null;

	$scope.applyVoucher = function() {
		var id = document.getElementById('voucher').value;
		if (id === '0') {
			$scope.voucherCost = 0;
			Swal.fire({
				icon: 'error',
				title: 'Remove vouchers success !',
				showConfirmButton: false,
				timer: 1500
			})
		} else {
			$http.get(`/rest/vouchers/${id}`).then(resp => {
				$scope.selectedVoucher = resp.data;
				var selectedVoucher = $scope.selectedVoucher.price;

				var formattedDate = new Date();

				var year = formattedDate.getFullYear();
				var month = String(formattedDate.getMonth() + 1).padStart(2, '0');
				var day = String(formattedDate.getDate()).padStart(2, '0');

				var now_date = `${year}-${month}-${day}`;

				if ($scope.selectedVoucher.quantity > 0 && $scope.selectedVoucher.end_date >= now_date) {
					$scope.voucherCost = parseFloat(selectedVoucher);
					$cart.tong;
					Swal.fire({
						icon: 'success',
						title: 'Add vouchers success !',
						showConfirmButton: false,
						timer: 1500
					})
				} else {
					$scope.voucherCost = 0;
					$cart.tong;
					Swal.fire({
						icon: 'error',
						title: 'Vouchers code has expired !',
						showConfirmButton: false,
						timer: 1500
					})
				}
			});
		}
	};

	$scope.useVoucher = function() {
		var id = document.getElementById('voucher').value;

		if (id !== '0') {
			// Giảm giá trị quantity đi 1
			if ($scope.selectedVoucher && $scope.selectedVoucher.quantity > 0) {
				$scope.selectedVoucher.quantity -= 1;
			}

			$http.put(`/rest/vouchers/${id}`, $scope.selectedVoucher).then(resp => {
			}).catch(error => {
				console.log("Error", error);
			});
		}
	};

	/////////////////////////////////////////////////////////
	$scope.addresss = [];
	$scope.address = function() {
		var username = $("#username").text();
		$http.get(`/rest/account-address/${username}`).then(resp => {
			$scope.addresss = resp.data;
		});
	}

	$scope.address();

	/////////////////////////////////////////////////////////




	var $cart = $scope.cart = {
		items: [],
		add(id, ram, color) {
			var item = this.items.find(item => item.id == id && item.ram == ram && item.color == color);

			if (item) {
				item.qty++;
				item.ram = $scope.ram;
				item.color = $scope.color;
				if (item.ram === '256G') {
					item.price += 500000;
				}
				if (item.ram === '512G') {
					item.price += 1000000;
				}
				if (item.ram === '1TB') {
					item.price += 2000000;
				}
				Swal.fire({
					icon: 'success',
					title: 'Success',
					showConfirmButton: false,
					timer: 1500
				});
			} else {
				$http.get(`/rest/products/${id}`).then(resp => {
					$scope.updateRam();
					$scope.updateColor();
					resp.data.qty = $scope.qty;
					resp.data.ram = $scope.ram;
					resp.data.color = $scope.color;
					resp.data.price1 = resp.data.price * (100 - resp.data.sale) / 100;
					if (resp.data.categories && (resp.data.categories.id === 5 || resp.data.categories.id === 6)) {
						resp.data.ram = '';
						resp.data.color = '';
					}
					if (resp.data.ram === '256G') {
						resp.data.price += 500000;
					}
					if (resp.data.ram === '512G') {
						resp.data.price += 1000000;
					}
					if (resp.data.ram === '1TB') {
						resp.data.price += 2000000;
					}
					this.items.push(resp.data);
					this.saveToLocalStorage();
					Swal.fire({
						icon: 'success',
						title: 'Success',
						showConfirmButton: false,
						timer: 1500
					});
				});
			}
		},
		remove(id) {
			var index = this.items.findIndex(item => item.id == id);
			this.items.splice(index, 1);
			this.saveToLocalStorage();
		},
		clear() {
			this.items = []
			this.saveToLocalStorage();
		},
		amt_of(item) {
			return (item.price * item.qty);
		},
		get count() {
			return this.items
				.map(item => item.qty)
				.reduce((total, qty) => total += qty, 0);
		},
		get amount() {
			return this.items
				.map(item => this.amt_of(item))
				.reduce((total, amt) => total += amt, 0);
		},
		get tong() {
			const shippingCost = 250000;
			const voucherCost = $scope.voucherCost;
			const total = $cart.amount + shippingCost - voucherCost;
			if (total > 50000000) {
				return total - shippingCost;
			}
			return total;
		},
		get ship() {
			const shippingCost = 250000;
			if (this.tong > 50000000) {
				return 0;
			}
			return shippingCost;
		},
		saveToLocalStorage() {
			this.items.forEach(item => {
				if (item.ram === '128G') {
					item.price = item.price1;
				} else if (item.ram === '256G') {
					item.price = item.price1 + 500000;
				} else if (item.ram === '512G') {
					item.price = item.price1 + 1000000;
				} else if (item.ram === '1TB') {
					item.price = item.price1 + 2000000;
				}
			});
			var json = JSON.stringify(angular.copy($scope.cart.items));
			localStorage.setItem("cart", json);
		},
		loadFromLocalStorage() {
			var json = localStorage.getItem("cart");
			this.items = json ? JSON.parse(json) : [];
		}
	}

	$cart.loadFromLocalStorage();


	// Favorite 	
	var $favorite = $scope.favorite = {
		favorite_items: [],
		add(id) {
			var favorite_item = this.favorite_items.find(favorite_item => favorite_item.id == id);
			Swal.fire({
				icon: 'success',
				title: 'Add to favorites !',
				showConfirmButton: false,
				timer: 1500
			})
			if (favorite_item) {
				Swal.fire({
					icon: 'error',
					title: 'Remove from favorites !',
					showConfirmButton: false,
					timer: 1500
				})
				favorite_item = false;
				var index = this.favorite_items.findIndex(favorite_item => favorite_item.id == id);
				this.favorite_items.splice(index, 1);
				this.saveFavoriteToLocalStorage();
			} else {
				$http.get(`/rest/products/${id}`).then(resp => {
					resp.data.qty = $scope.qty;
					resp.data.price1 = resp.data.price * (100 - resp.data.sale) / 100;
					this.favorite_items.push(resp.data);
					this.saveFavoriteToLocalStorage();
				})
			}
		},
		remove(id) {
			id = false;
			var index = this.favorite_items.findIndex(favorite_item => favorite_item.id == id);
			this.favorite_items.splice(index, 1);
			Swal.fire({
				icon: 'error',
				title: 'Remove from favorites !',
				showConfirmButton: false,
				timer: 1500
			})
			this.saveFavoriteToLocalStorage();

		},
		clear() {
			this.favorite_items = []
			this.saveFavoriteToLocalStorage();
		},
		get count() {
			return this.favorite_items
				.map(favorite_item => favorite_item.qty)
				.reduce((total, qty) => total += qty, 0);
		},
		saveFavoriteToLocalStorage() {
			var json = JSON.stringify(angular.copy(this.favorite_items));
			localStorage.setItem("favorite", json);
		},
		loadFavoriteFromLocalStorage() {
			var json = localStorage.getItem("favorite");
			this.favorite_items = json ? JSON.parse(json) : [];
		}
	}

	$favorite.loadFavoriteFromLocalStorage();


	// Sản Phẩm Vừa Xem

	var $viewed = $scope.viewed = {
		viewed_items: [],
		add(id) {
			var viewed_item = this.viewed_items.find(viewed_item => viewed_item.id == id);
			if (viewed_item) {
				viewed_item.date = new Date();
				this.saveViewedToLocalStorage();
			} else {
				$http.get(`/rest/products/${id}`).then(resp => {
					resp.data.qty = $scope.qty;
					resp.data.date = new Date();
					resp.data.price1 = resp.data.price * (100 - resp.data.sale) / 100;
					this.viewed_items.push(resp.data);
					this.saveViewedToLocalStorage();
				})
			}
		},
		remove(id) {
			id = false;
			var index = this.viewed_items.findIndex(viewed_item => viewed_item.id == id);
			this.viewed_items.splice(index, 1);
			Swal.fire({
				icon: 'error',
				title: 'Remove from viewed !',
				showConfirmButton: false,
				timer: 1000
			})
			this.saveViewedToLocalStorage();

		},
		clear() {
			this.viewed_items = []
			this.saveViewedToLocalStorage();
		},
		get count() {
			return this.viewed_items
				.map(viewed_item => viewed_item.qty)
				.reduce((total, qty) => total += qty, 0);
		},
		saveViewedToLocalStorage() {
			var json = JSON.stringify(angular.copy(this.viewed_items));
			localStorage.setItem("viewed", json);
		},
		loadViewedFromLocalStorage() {
			var json = localStorage.getItem("viewed");
			this.viewed_items = json ? JSON.parse(json) : [];
		}
	}

	$viewed.loadViewedFromLocalStorage();


	// API Provinces
	const host = "https://provinces.open-api.vn/api/";
	var callAPI = (api) => {
		return axios.get(api)
			.then((response) => {
				renderData(response.data, "province");
			});
	}
	callAPI('https://provinces.open-api.vn/api/?depth=1');
	var callApiDistrict = (api) => {
		return axios.get(api)
			.then((response) => {
				renderData(response.data.districts, "district");
			});
	}
	var callApiWard = (api) => {
		return axios.get(api)
			.then((response) => {
				renderData(response.data.wards, "ward");
			});
	}

	var renderData = (array, select) => {
		let row = ' <option disable value="">Chọn Thành Phố / Tỉnh</option>';
		array.forEach(element => {
			row += `<option value="${element.code}">${element.name}</option>`
		});
		document.querySelector("#" + select).innerHTML = row
	}

	$("#province").change(() => {
		callApiDistrict(host + "p/" + $("#province").val() + "?depth=2");
		printResult();
	});
	$("#district").change(() => {
		callApiWard(host + "d/" + $("#district").val() + "?depth=2");
		printResult();
	});

	$scope.selectedWards = [];
	$scope.selectedProvice = [];
	$scope.selectedDistrict = [];

	$("#ward").change(() => {
		let selectedDistrict = $("#district option:selected").text();
		let selectedProvice = $("#province option:selected").text();
		let selectedWard = $("#ward option:selected").text();
		$scope.order.ship_ward = selectedWard;
		$scope.order.ship_province = selectedProvice;
		$scope.order.ship_district = selectedDistrict;
	});


	// Đặt hàng - Tiền Mặt
	$scope.order = {
		ship_address: "",
		ship_notes: "",
		ship_phone: "",
		ship_ward: "",
		ship_district: "",
		ship_province: "",
		ship_delivery: "",
		create_date: new Date(),
		accounts: { username: $("#username").text() },
		order_status: { id: "Pending" },
		account_address: { id: "" },
		get orderDetails() {
			return $scope.cart.items.map(item => {
				return {
					products: { id: item.id },
					price: item.price,
					quantity: item.qty,
					ram: item.ram,
					color: item.color
				}
			});
		},
		get total() {
			return $cart.tong - ($scope.voucherCost || 0);
		},
		purchase() {
			var order = angular.copy(this);
			order.ship_pay = "Thanh Toán Tiền Mặt";
			order.total = $cart.tong;
			order.ship_fee = $cart.ship;
			order.ship_delivery = document.getElementById("shipping").value;
			order.account_address.id = document.getElementById("address").value || 1;
			if ($cart.amount == 0) {
				Swal.fire({
					icon: 'error',
					title: 'Error !',
					showConfirmButton: false,
					timer: 1500
				})
			} else {
				$http.post("/rest/orders", order).then(resp => {
					Swal.fire({
						icon: 'success',
						title: 'Payment Success !',
						showConfirmButton: false,
						timer: 2500
					})
					$scope.useVoucher();
					$scope.cart.clear();
					$scope.voucherCost = null;
					setTimeout(function() {
						location.href = "/order/detail/" + resp.data.id;
					}, 2500);
				}).catch(error => {
					Swal.fire({
						icon: 'error',
						title: 'Error !',
						showConfirmButton: false,
						timer: 1500
					})
					console.log(error)
				})
			}

		},
		paypal() {
			var order = angular.copy(this);
			order.ship_pay = "Đã Thanh Toán Paypal";
			order.total = $cart.tong;
			order.ship_fee = $cart.ship;
			order.ship_delivery = document.getElementById("shipping").value;
			order.account_address.id = document.getElementById("address").value || 1;
			if ($cart.amount == 0 || $cart.amount == null) {
				Swal.fire({
					icon: 'error',
					title: 'Error !',
					showConfirmButton: false,
					timer: 1500
				})
			} else {
				$http.post("/rest/orders", order).then(resp => {
					Swal.fire({
						icon: 'success',
						title: 'Payment Success !',
						showConfirmButton: false,
						timer: 2500
					})
					$scope.useVoucher();
					$scope.cart.clear();
					$scope.voucherCost = null;
					setTimeout(function() {
						location.href = "/order/detail/" + resp.data.id;
					}, 2500);

				}).catch(error => {
					Swal.fire({
						icon: 'error',
						title: 'Error !',
						showConfirmButton: false,
						timer: 1500
					})
					console.log(error)
				})
			}

		},
		vnpay() {
			var order = angular.copy(this);
			order.ship_pay = "Đã Thanh Toán VNPay";
			order.total = $cart.tong;
			order.account_address.id = document.getElementById("address").value;
			if ($cart.amount == 0 || $cart.amount == null) {
				Swal.fire({
					icon: 'error',
					title: 'Error !',
					showConfirmButton: false,
					timer: 1500
				})
			} else {
				$http.post("/rest/orders", order).then(resp => {
					Swal.fire({
						icon: 'success',
						title: 'Payment Success !',
						showConfirmButton: false,
						timer: 2500
					})
					$scope.useVoucher();
					$scope.cart.clear();
					$scope.voucherCost = null;
					setTimeout(function() {
						location.href = "/order/detail/" + resp.data.id;
					}, 2500);

				}).catch(error => {
					Swal.fire({
						icon: 'error',
						title: 'Error !',
						showConfirmButton: false,
						timer: 1500
					})
					console.log(error)
				})
			}

		}
	}


	// Khởi tạo biến để giữ giá trị cho PayPal
	$scope.paypalAmount = Math.round(($scope.cart.amount + 250000) / 24000);
	// Khởi tạo biến để giữ giá trị cho PayPal


	$scope.vnpay = function() {
		Swal.fire({
			icon: 'error',
			title: 'Payment VNPay !',
			showConfirmButton: false,
			timer: 2000
		})
	};

	$scope.momo = function() {
		Swal.fire({
			icon: 'error',
			title: 'Payment Momo !',
			showConfirmButton: false,
			timer: 2000
		})
	};

	// Rating
	$scope.formRV = {};

	$scope.initRV = function() {
		$scope.formRV = {
			image: 'upload.png',
		}
	}

	$scope.initRV();


	$scope.createReview = function(id) {
		console.log(id);

		Swal.fire({
			icon: 'success',
			title: 'Add Successfully !',
			showConfirmButton: false,
			timer: 1000
		})
	}

	$scope.imageRate = function(files) {
		var data = new FormData();
		data.append('file', files[0]);
		$http.post('/rest/upload/images', data, {
			transformRequest: angular.identity,
			headers: { 'Content-Type': undefined }
		}).then(resp => {
			$scope.formRV.image = resp.data.name;
		}).catch(error => {
			alert("Upload Image False !");
			console.log("Error", error);
		})
	}

})