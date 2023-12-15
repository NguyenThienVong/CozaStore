app.controller("order-ctrl", function($scope, $http) {

	$scope.items = [];
	$scope.itemsPending = [];
	$scope.itemsConfirm = [];
	$scope.itemsDelivery = [];
	$scope.itemsComplete = [];
	$scope.itemsCancel = [];
	$scope.tops = [];


	$scope.form = {};
	$scope.form1 = {};

	$scope.labels = [];
	$scope.data = [];



	$scope.getTotalCount = function() {
		var totalCount = 0;
		for (var i = 0; i < $scope.tops.length; i++) {
			totalCount += $scope.tops[i].count;
		}
		return totalCount;
	};
	


	$scope.getDate = function() {
		var startDateValue = $scope.form1.startDate;
		var endDateValue = $scope.form1.endDate;
		$http.get(`/rest/revenueOrder/${startDateValue}/${endDateValue}`).then(resp => {
			$scope.tops = resp.data;

			// Xử lý dữ liệu để đưa vào biểu đồ
			$scope.labels = $scope.tops.map(function(item) {
				return item.order_status.id;
			});
			$scope.data = $scope.tops.map(function(item) {
				return item.count;
			});
			$scope.totalAmounts = $scope.tops.map(function(item) {
				return item.sum;
			});

			// Vẽ biểu đồ
			var ctx = document.getElementById('myChart').getContext('2d');
			var myChart = new Chart(ctx, {
				type: 'line',
				data: {
					labels: $scope.labels,
					datasets: [
						{
							label: 'Count Order',
							data: $scope.data,
							backgroundColor: 'rgba(255, 99, 132, 0.2)',
							borderColor: 'rgba(255, 99, 132, 1)',
							borderWidth: 1,
							barPercentage: 0.4,
						},
						{
							label: 'Sum Order',
							data: $scope.totalAmounts,
							backgroundColor: 'rgba(75, 192, 192, 0.2)',
							borderColor: 'rgba(75, 192, 192, 1)',
							borderWidth: 1,
							barPercentage: 0.4,
						}
					]
				},
				options: {
					scales: {
						y: [{
							beginAtZero: true
						}]
					}
				}
			});
		});
	}


	$scope.init = function() {
		$http.get(`/rest/orders/all-orders`).then(resp => {
			$scope.items = resp.data;
			$scope.form = $scope.items[0];
		})

		$http.get(`/rest/orders/order-pending`).then(resp => {
			$scope.itemsPending = resp.data;
		});

		$http.get(`/rest/orders/order-confirm`).then(resp => {
			$scope.itemsConfirm = resp.data;
		});

		$http.get(`/rest/orders/order-delivery`).then(resp => {
			$scope.itemsDelivery = resp.data;
		});

		$http.get(`/rest/orders/order-complete`).then(resp => {
			$scope.itemsComplete = resp.data;
		});

		$http.get(`/rest/orders/order-cancel`).then(resp => {
			$scope.itemsCancel = resp.data;
		});

		$scope.getOrderStatus = '';

		$http.get(`/rest/revenueOrder/all`).then(resp => {
			$scope.tops = resp.data;

			// Xử lý dữ liệu để đưa vào biểu đồ
			$scope.labels = $scope.tops.map(function(item) {
				return item.order_status.id;
			});
			$scope.data = $scope.tops.map(function(item) {
				return item.count;
			});
			$scope.totalAmounts = $scope.tops.map(function(item) {
				return item.sum;
			});

			// Vẽ biểu đồ
			var ctx = document.getElementById('myChart').getContext('2d');
			var myChart = new Chart(ctx, {
				type: 'line',
				data: {
					labels: $scope.labels,
					datasets: [
						{
							label: 'Count Order',
							data: $scope.data,
							backgroundColor: 'rgba(255, 99, 132, 0.2)',
							borderColor: 'rgba(255, 99, 132, 1)',
							borderWidth: 1,
							barPercentage: 0.4,
						},
						{
							label: 'Sum Order',
							data: $scope.totalAmounts,
							backgroundColor: 'rgba(75, 192, 192, 0.2)',
							borderColor: 'rgba(75, 192, 192, 1)',
							borderWidth: 1,
							barPercentage: 0.4,
						}
					]
				},
				options: {
					scales: {
						y: [{
							beginAtZero: true // Bắt đầu từ giá trị 0
						}]
					}
				}
			});

		});
	}



	$scope.init();

	$scope.findByAllOrder = function() {
		var kw = $scope.kw;
		if (kw.length > 0) {
			$http.get(`/rest/orders/search/${kw}`).then(resp => {
				$scope.items = resp.data;
			});
		} else {
			$http.get(`/rest/orders/all-orders`).then(resp => {
				$scope.items = resp.data;
			});
		}
	}



	$scope.formDetail = [];

	$scope.edit = function(item) {
		$scope.form = angular.copy(item);
		$http.get(`/rest/orders/load-orders-detail/${item.id}`).then(resp => {
			$scope.formDetail = resp.data;
			$scope.getOrderStatus = $scope.form.order_status.id;
		})
	}

	$scope.confirm = function() {
		var item = angular.copy($scope.form);
		item.order_status.id = 'Confirm';
		$scope.getOrderStatus = 'Confirm';

		Swal.fire({
			icon: 'success',
			title: 'Successfully !',
			showConfirmButton: false,
			timer: 1500
		});

		$http.put(`/rest/orders/${item.id}`, item)
			.then(function(response) {
				console.log('Success:', response.data);
			})
			.catch(function(error) {
				console.error('Error:', error);
			})
			.finally(function() {
				$http.get(`/rest/orders/order-confirm`).then(resp => {
					$scope.itemsConfirm = resp.data;
				});
			});

		$("#myTab li:eq(2) a").tab('show');
	};



	$scope.delivery = function() {
		var item = angular.copy($scope.form);
		item.order_status.id = 'Delivery';
		$scope.getOrderStatus = 'Delivery';

		Swal.fire({
			icon: 'success',
			title: 'Successfully !',
			showConfirmButton: false,
			timer: 1500
		})
		$http.put(`/rest/orders/${item.id}`, item)
			.then(function(response) {
				console.log('Success:', response.data);
			})
			.catch(function(error) {
				console.error('Error:', error);
			})
			.finally(function() {
				$http.get(`/rest/orders/order-delivery`).then(resp => {
					$scope.itemsDelivery = resp.data;
				});
			});

		$("#myTab li:eq(3) a").tab('show');
	};

	$scope.complete = function() {
		var item = angular.copy($scope.form);
		item.order_status.id = 'Complete';
		$scope.getOrderStatus = 'Complete';

		Swal.fire({
			icon: 'success',
			title: 'Successfully !',
			showConfirmButton: false,
			timer: 1500
		})
		$http.put(`/rest/orders/${item.id}`, item)
			.then(function(response) {
				console.log('Success:', response.data);
			})
			.catch(function(error) {
				console.error('Error:', error);
			})
			.finally(function() {
				$http.get(`/rest/orders/order-complete`).then(resp => {
					$scope.itemsComplete = resp.data;
				});
			});

		$("#myTab li:eq(4) a").tab('show');
	};

	$scope.cancel = function() {
		var item = angular.copy($scope.form);
		item.order_status.id = 'Cancel';
		$scope.getOrderStatus = 'Cancel';

		Swal.fire({
			icon: 'success',
			title: 'Successfully !',
			showConfirmButton: false,
			timer: 1500
		})
		$http.put(`/rest/orders/${item.id}`, item)
			.then(function(response) {
				console.log('Success:', response.data);
			})
			.catch(function(error) {
				console.error('Error:', error);
			})
			.finally(function() {
				$http.get(`/rest/orders/order-cancel`).then(resp => {
					$scope.itemsCancel = resp.data;
				});
			});
		$("#myTab li:eq(5) a").tab('show');
	};


	$scope.pager = {
		page: 0,
		size: 10,
		get items() {
			var start = this.page * this.size;
			return $scope.items.slice(start, start + this.size);
		},
		get count() {
			return Math.ceil(1.0 * $scope.items.length / this.size);
		},
		first() {
			this.page = 0;
		},
		pre() {
			this.page--;
			if (this.page < 0) {
				this.last();
			}
		},
		next() {
			this.page++;
			if (this.page >= this.count) {
				this.first();
			}
		},
		last() {
			this.page = this.count - 1;
		}

	}


	$scope.pagerPending = {
		page: 0,
		size: 10,
		get itemsPending() {
			var start = this.page * this.size;
			return $scope.itemsPending.slice(start, start + this.size);
		},
		get count() {
			return Math.ceil(1.0 * $scope.itemsPending.length / this.size);
		},
		first() {
			this.page = 0;
		},
		pre() {
			this.page--;
			if (this.page < 0) {
				this.last();
			}
		},
		next() {
			this.page++;
			if (this.page >= this.count) {
				this.first();
			}
		},
		last() {
			this.page = this.count - 1;
		}
	}


	$scope.pagerConfirm = {
		page: 0,
		size: 10,
		get itemsConfirm() {
			var start = this.page * this.size;
			return $scope.itemsConfirm.slice(start, start + this.size);
		},
		get count() {
			return Math.ceil(1.0 * $scope.itemsConfirm.length / this.size);
		},
		first() {
			this.page = 0;
		},
		pre() {
			this.page--;
			if (this.page < 0) {
				this.last();
			}
		},
		next() {
			this.page++;
			if (this.page >= this.count) {
				this.first();
			}
		},
		last() {
			this.page = this.count - 1;
		}
	}

	$scope.pagerDelivery = {
		page: 0,
		size: 10,
		get itemsDelivery() {
			var start = this.page * this.size;
			return $scope.itemsDelivery.slice(start, start + this.size);
		},
		get count() {
			return Math.ceil(1.0 * $scope.itemsDelivery.length / this.size);
		},
		first() {
			this.page = 0;
		},
		pre() {
			this.page--;
			if (this.page < 0) {
				this.last();
			}
		},
		next() {
			this.page++;
			if (this.page >= this.count) {
				this.first();
			}
		},
		last() {
			this.page = this.count - 1;
		}
	}


	$scope.pagerComplete = {
		page: 0,
		size: 10,
		get itemsComplete() {
			var start = this.page * this.size;
			return $scope.itemsComplete.slice(start, start + this.size);
		},
		get count() {
			return Math.ceil(1.0 * $scope.itemsComplete.length / this.size);
		},
		first() {
			this.page = 0;
		},
		pre() {
			this.page--;
			if (this.page < 0) {
				this.last();
			}
		},
		next() {
			this.page++;
			if (this.page >= this.count) {
				this.first();
			}
		},
		last() {
			this.page = this.count - 1;
		}
	}




	$scope.pagerCancel = {
		page: 0,
		size: 10,
		get itemsCancel() {
			var start = this.page * this.size;
			return $scope.itemsCancel.slice(start, start + this.size);
		},
		get count() {
			return Math.ceil(1.0 * $scope.itemsCancel.length / this.size);
		},
		first() {
			this.page = 0;
		},
		pre() {
			this.page--;
			if (this.page < 0) {
				this.last();
			}
		},
		next() {
			this.page++;
			if (this.page >= this.count) {
				this.first();
			}
		},
		last() {
			this.page = this.count - 1;
		}
	}

});
