package com.poly.controller.client;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.poly.dao.AccountsDAO;
import com.poly.dao.ProductsDAO;
import com.poly.dao.ReviewsDAO;
import com.poly.entity.Order_detail;
import com.poly.entity.Products;
import com.poly.entity.Reviews;
import com.poly.service.OrderDetailService;
import com.poly.service.ProductsService;

@Controller
@RequestMapping("home")
public class ProductController {

	@Autowired
	ProductsService productsService;

	@Autowired
	ProductsDAO productsDAO;

	@Autowired
	ReviewsDAO reviewsDAO;

	@Autowired
	AccountsDAO accountsDAO;

	@GetMapping
	public Page<Products> getProducts(@RequestParam("cid") Optional<String> cid,
			@RequestParam("keywords") Optional<String> kw, @RequestParam("p") Optional<Integer> p) {
		Pageable pageable = PageRequest.of(p.orElse(0), 16);
		Page<Products> page = productsDAO.findAll(pageable);
		return page;
	}

	@RequestMapping("product/list")
	public String list(Model model, @RequestParam("cid") Optional<String> cid,
			@RequestParam("keywords") Optional<String> kw, @RequestParam("p") Optional<Integer> p,
			@RequestParam("min") Optional<Double> min, @RequestParam("max") Optional<Double> max,
			@RequestParam("sort_name") Optional<String> sortName,
			@RequestParam("sort_price") Optional<String> sortPrice, @RequestParam("banchay") Optional<String> banchay) {
		Pageable pageable = PageRequest.of(p.orElse(0), 16);
		Integer pn = p.orElse(0);
		if (pn < 1) {
			pn = 1;
		}
		model.addAttribute("sid", "?");
		model.addAttribute("spgbc", "?");
		String banchayy = banchay.orElse("");
		if (cid.isPresent() == true && banchayy.equals("true")) {
			String cidd = cid.orElse("");
			Page<Products> page = productsDAO.SanPhamBanChaybyid(cidd, pageable);
			model.addAttribute("sc", "?cid" + cidd + "&banchay=true&");
			model.addAttribute("page", page);
		} else if (cid.isPresent() == false && banchayy.equals("true")) {
			Page<Products> page = productsDAO.SanPhamBanChay(pageable);
			model.addAttribute("sc", "?banchay=true&");
			model.addAttribute("page", page);
		} else if (cid.isPresent() == true && min.isPresent() == true && max.isPresent() == true
				&& sortName.isPresent() == true) {
			String cidd = cid.orElse("");
			double minn = min.orElse(0.0);
			double maxx = max.orElse(0.0);
			String sortNamee = sortName.orElse("");
			if (sortNamee.equals("increase")) {
				Page<Products> page = productsDAO.nameidminmaxasc(cidd, minn, maxx, pageable);
				model.addAttribute("sid", "?cid=" + cidd + "&min=" + minn + "&max=" + maxx + "&");
				model.addAttribute("sc", "?min=" + minn + "&max=" + maxx + "&sort_name=increase" + "&");
				model.addAttribute("scp", "?min=" + minn + "&max=" + maxx + "&sort_name=increase&" + "p=" + (pn + 1));
				model.addAttribute("scpt", "?min=" + minn + "&max=" + maxx + "&sort_name=increase&" + "p=" + (pn - 1));
				model.addAttribute("page", page);
			} else if (sortNamee.equals("reduce")) {
				Page<Products> page = productsDAO.nameidminmaxdesc(cidd, minn, maxx, pageable);
				model.addAttribute("sid", "?cid=" + cidd + "&min=" + minn + "&max=" + maxx + "&");
				model.addAttribute("sc", "?cid=" + cidd + "&min=" + minn + "&max=" + maxx + "&sort_name=reduce" + "&");
				model.addAttribute("scp",
						"?cid=" + cidd + "&min=" + minn + "&max=" + maxx + "&sort_name=reduce&" + "p=" + (pn + 1));
				model.addAttribute("scpt",
						"?cid=" + cidd + "&min=" + minn + "&max=" + maxx + "&sort_name=reduce&" + "p=" + (pn - 1));
				model.addAttribute("page", page);
			}
		} else if (cid.isPresent() == true && min.isPresent() == true && max.isPresent() == true
				&& sortPrice.isPresent() == true) {
			String cidd = cid.orElse("");
			double minn = min.orElse(0.0);
			double maxx = max.orElse(0.0);
			String sortPricee = sortPrice.orElse("");
			if (sortPricee.equals("increase")) {
				Page<Products> page = productsDAO.priceidminmaxasc(cidd, minn, maxx, pageable);
				model.addAttribute("sid", "?cid=" + cidd + "&min=" + minn + "&max=" + maxx + "&");
				model.addAttribute("sc", "?min=" + minn + "&max=" + maxx + "&sort_price=increase" + "&");
				model.addAttribute("scp", "?min=" + minn + "&max=" + maxx + "&sort_price=increase&" + "p=" + (pn + 1));
				model.addAttribute("scpt", "?min=" + minn + "&max=" + maxx + "&sort_price=increase&" + "p=" + (pn - 1));
				model.addAttribute("page", page);
			} else if (sortPricee.equals("reduce")) {
				Page<Products> page = productsDAO.priceidminmaxdesc(cidd, minn, maxx, pageable);
				model.addAttribute("sid", "?cid=" + cidd + "&min=" + minn + "&max=" + maxx + "&");
				model.addAttribute("sc", "?cid=" + cidd + "&min=" + minn + "&max=" + maxx + "&sort_price=reduce" + "&");
				model.addAttribute("scp",
						"?cid=" + cidd + "&min=" + minn + "&max=" + maxx + "&sort_price=reduce&" + "p=" + (pn + 1));
				model.addAttribute("scpt",
						"?cid=" + cidd + "&min=" + minn + "&max=" + maxx + "&sort_price=reduce&" + "p=" + (pn - 1));
				model.addAttribute("page", page);
			}
		} else if (min.isPresent() == true && max.isPresent() == true && sortPrice.isPresent() == true) {
			double minn = min.orElse(0.0);
			double maxx = max.orElse(0.0);
			String sortPricee = sortPrice.orElse("");
			if (sortPricee.equals("increase")) {
				Page<Products> page = productsDAO.Price2priceup(minn, maxx, pageable);
				model.addAttribute("sid", "?min=" + minn + "&max=" + maxx + "&");
				model.addAttribute("sc", "?min=" + minn + "&max=" + maxx + "&sort_price=increase" + "&");
				model.addAttribute("scp", "?min=" + minn + "&max=" + maxx + "&sort_price=increase&" + "p=" + (pn + 1));
				model.addAttribute("scpt", "?min=" + minn + "&max=" + maxx + "&sort_price=increase&" + "p=" + (pn - 1));
				model.addAttribute("page", page);
			}

		} else if (min.isPresent() == true && max.isPresent() == true && sortPrice.isPresent() == true) {
			double minn = min.orElse(0.0);
			double maxx = max.orElse(0.0);
			String sortPricee = sortPrice.orElse("");
			if (sortPricee.equals("increase")) {
				Page<Products> page = productsDAO.Price2priceup(minn, maxx, pageable);
				model.addAttribute("sid", "?min=" + minn + "&max=" + maxx + "&");
				model.addAttribute("sc", "?min=" + minn + "&max=" + maxx + "&sort_price=increase" + "&");
				model.addAttribute("scp", "?min=" + minn + "&max=" + maxx + "&sort_price=increase&" + "p=" + (pn + 1));
				model.addAttribute("scpt", "?min=" + minn + "&max=" + maxx + "&sort_price=increase&" + "p=" + (pn - 1));
				model.addAttribute("page", page);
			} else if (sortPricee.equals("reduce")) {
				Page<Products> page = productsDAO.Price2pricedown(minn, maxx, pageable);
				model.addAttribute("sid", "?min=" + minn + "&max=" + maxx + "&");
				model.addAttribute("sc", "?min=" + minn + "&max=" + maxx + "&sort_price=reduce" + "&");
				model.addAttribute("scp", "?min=" + minn + "&max=" + maxx + "&sort_price=reduce&" + "p=" + (pn + 1));
				model.addAttribute("scpt", "?min=" + minn + "&max=" + maxx + "&sort_price=reduce&" + "p=" + (pn - 1));
				model.addAttribute("page", page);
			}

		} else

		if (min.isPresent() == true && max.isPresent() == true && sortName.isPresent() == true) {
			double minn = min.orElse(0.0);
			double maxx = max.orElse(0.0);
			String sortNamee = sortName.orElse("");
			if (sortNamee.equals("reduce")) {
				Page<Products> page = productsDAO.Price2nameup(minn, maxx, pageable);
				model.addAttribute("sid", "?min=" + minn + "&max=" + maxx + "&");
				model.addAttribute("sc", "?min=" + minn + "&max=" + maxx + "&sort_name=increase" + "&");
				model.addAttribute("scp", "?min=" + minn + "&max=" + maxx + "&sort_name=increase&" + "p=" + (pn + 1));
				model.addAttribute("scpt", "?min=" + minn + "&max=" + maxx + "&sort_name=increase&" + "p=" + (pn - 1));
				model.addAttribute("page", page);
			} else if (sortNamee.equals("increase")) {
				Page<Products> page = productsDAO.Price2namedown(minn, maxx, pageable);
				model.addAttribute("sid", "?min=" + minn + "&max=" + maxx + "&");
				model.addAttribute("sc", "?min=" + minn + "&max=" + maxx + "&sort_name=reduce" + "&");
				model.addAttribute("scp", "?min=" + minn + "&max=" + maxx + "&sort_name=reduce&" + "p=" + (pn + 1));
				model.addAttribute("scpt", "?min=" + minn + "&max=" + maxx + "&sort_name=reduce&" + "p=" + (pn - 1));
				model.addAttribute("page", page);
			}

		}

		else if (cid.isPresent() == true && min.isPresent() == true && max.isPresent() == true) {
			try {
				String cidd = cid.orElse("");
				double minn = min.orElse(0.0);
				double maxx = max.orElse(0.0);

				model.addAttribute("sid", "?cid=" + cidd + "&min=" + minn + "&max=" + maxx + "&");

				Page<Products> page = productsDAO.SortbyPriceCategory(cidd, minn, maxx, pageable);
				model.addAttribute("spg", "?cid=" + cidd + "&");
				model.addAttribute("scp", "?cid=" + cidd + "&min=" + minn + "&max=" + maxx + "&" + "p=" + (pn + 1));
				model.addAttribute("scpt", "?cid=" + cidd + "&min=" + minn + "&max=" + maxx + "&" + "p=" + (pn - 1));
				model.addAttribute("sc", "?cid=" + cidd + "&min=" + minn + "&max=" + maxx + "&");
				model.addAttribute("page", page);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}

		else if (cid.isPresent() == true && sortName.isPresent() == true) {
			try {
				String cidd = cid.orElse("");
				String sortNamee = sortName.orElse("");
				if (sortNamee.equals("increase")) {
					Page<Products> page = productsDAO.HsortByField(cidd, pageable);
					model.addAttribute("sid", "?cid=" + cidd + "&");
					model.addAttribute("spg", "?cid=" + cidd + "&");
					model.addAttribute("scp", "?cid=" + cidd + "&sort_name=increase" + "&" + "p=" + (pn + 1));
					model.addAttribute("scpt", "?cid=" + cidd + "&sort_name=increase" + "&" + "p=" + (pn - 1));
					model.addAttribute("sc", "?cid=" + cidd + "&sort_name=increase" + "&");
					model.addAttribute("page", page);
				}
				if (sortNamee.equals("reduce")) {
					Page<Products> page = productsDAO.sortNameDesc(cidd, pageable);
					model.addAttribute("sid", "?cid=" + cidd + "&");
					model.addAttribute("spg", "?cid=" + cidd + "&");
					model.addAttribute("scp", "?cid=" + cidd + "&sort_name=reduce" + "&" + "p=" + (pn + 1));
					model.addAttribute("scpt", "?cid=" + cidd + "&sort_name=reduce" + "&" + "p=" + (pn - 1));
					model.addAttribute("sc", "?cid=" + cidd + "&sort_name=reduce" + "&");
					model.addAttribute("page", page);
				}

			} catch (Exception e) {
				// TODO: handle exception
			}

		} else if (cid.isPresent() == false && sortName.isPresent() == true) {
			try {
				String sortNamee = sortName.orElse("");
				if (sortNamee.equals("increase")) {
					Page<Products> page = productsDAO.HsortByNameup(pageable);
					model.addAttribute("sc", "?sort_name=increase" + "&");
					model.addAttribute("scp", "?sort_name=increase" + "&" + "p=" + (pn + 1));
					model.addAttribute("scpt", "?sort_name=increase" + "&" + "p=" + (pn - 1));
					model.addAttribute("page", page);
				} else if (sortNamee.equals("reduce")) {
					Page<Products> page = productsDAO.HsortByNamedown(pageable);
					model.addAttribute("sc", "?sort_name=reduce" + "&");
					model.addAttribute("scp", "?sort_name=reduce" + "&" + "p=" + (pn + 1));
					model.addAttribute("scpt", "?sort_name=reduce" + "&" + "p=" + (pn - 1));
					model.addAttribute("page", page);
				}
			} catch (Exception e) {
				// TODO: handle exception
			}

		} else if (cid.isPresent() == true && sortPrice.isPresent() == true) {
			try {
				String cidd = cid.orElse("");
				String sortPricee = sortPrice.orElse("");
				if (sortPricee.equals("increase")) {
					Page<Products> page = productsDAO.PsortByField(cidd, pageable);
					model.addAttribute("sid", "?cid=" + cidd + "&");
					model.addAttribute("spg", "?cid=" + cidd + "&");
					model.addAttribute("sc", "?cid=" + cidd + "&sort_price=increase" + "&");
					model.addAttribute("scp", "?cid=" + cidd + "&sort_price=increase" + "&" + "p=" + (pn + 1));
					model.addAttribute("scpt", "?cid=" + cidd + "&sort_price=increase" + "&" + "p=" + (pn - 1));
					model.addAttribute("page", page);
				}
				if (sortPricee.equals("reduce")) {
					Page<Products> page = productsDAO.PDsortByField(cidd, pageable);
					model.addAttribute("sid", "?cid=" + cidd + "&");
					model.addAttribute("spg", "?cid=" + cidd + "&");
					model.addAttribute("sc", "?cid=" + cidd + "&sort_price=reduce" + "&");
					model.addAttribute("scp", "?cid=" + cidd + "&sort_price=reduce" + "&" + "p=" + (pn + 1));
					model.addAttribute("scpt", "?cid=" + cidd + "&sort_price=reduce" + "&" + "p=" + (pn - 1));
					model.addAttribute("page", page);
				}

			} catch (Exception e) {
				// TODO: handle exception
			}

		} else if (cid.isPresent() == false && sortPrice.isPresent() == true) {
			try {
				String sortPricee = sortPrice.orElse("");
				if (sortPricee.equals("increase")) {
					Page<Products> page = productsDAO.DsortByPrice(pageable);
					model.addAttribute("sc", "?sort_price=increase" + "&");
					model.addAttribute("scp", "?sort_price=increase" + "&" + "p=" + (pn + 1));
					model.addAttribute("scpt", "?sort_price=increase" + "&" + "p=" + (pn - 1));
					model.addAttribute("page", page);
				} else if (sortPricee.equals("reduce")) {
					Page<Products> page = productsDAO.UsortByPrice(pageable);
					model.addAttribute("sc", "?sort_price=reduce" + "&");
					model.addAttribute("scp", "?sort_price=reduce" + "&" + "p=" + (pn + 1));
					model.addAttribute("scpt", "?sort_price=reduce" + "&" + "p=" + (pn - 1));
					model.addAttribute("page", page);
				}
			} catch (Exception e) {
				// TODO: handle exception
			}

		} else if (cid.isPresent()) {
			String cidd = cid.orElse("");
			Page<Products> page = productsDAO.findByCategoryId(cidd, pageable);
			model.addAttribute("page", page);
			model.addAttribute("sc", "?cid=" + cidd + "&");
			model.addAttribute("spg", "?cid=" + cidd + "&");
			model.addAttribute("spgbc", "?cid=" + cidd + "&");
			model.addAttribute("scp", "?cid=" + cidd + "&" + "p=" + (pn + 1));
			model.addAttribute("scpt", "?cid=" + cidd + "&" + "p=" + (pn - 1));
			model.addAttribute("sid", "?cid=" + cidd + "&");
		} else if (kw.isPresent()) {
			String keywords = kw.orElse("");
			Page<Products> page = productsDAO.findAllByNameLike(keywords + "%", pageable);
			model.addAttribute("sc", "?keywords=" + keywords + "&");
			model.addAttribute("scp", "?keywords=" + keywords + "&" + "p=" + (pn + 1));
			model.addAttribute("scpt", "?keywords=" + keywords + "&" + "p=" + (pn - 1));
			model.addAttribute("spg", "pricemix?");
			model.addAttribute("page", page);
		} else {
			Page<Products> page = getProducts(cid, kw, p);
			model.addAttribute("sc", "?");
			model.addAttribute("scp", "?p=" + (pn + 1));
			model.addAttribute("scpt", "?p=" + (pn - 1));
			model.addAttribute("spg", "pricemix?");
			model.addAttribute("page", page);

		}
		if (cid.isEmpty()) {
			model.addAttribute("spg", "pricemix?");
		}
		return "user/product/product";
	}

	@RequestMapping("/product/list/pricemix")
	public String sortPrice(Model model, @RequestParam("p") Optional<Integer> p,
			@RequestParam("min") Optional<Double> min, @RequestParam("max") Optional<Double> max) {
		Pageable pageable = PageRequest.of(p.orElse(0), 16);
		Integer pn = p.orElse(0);
		if (pn < 1) {
			pn = 1;
		}
		double minn = min.orElse(0.0);
		double maxx = max.orElse(0.0);
		Page<Products> page = productsDAO.SortByPrice(minn, maxx, pageable);
		model.addAttribute("sc", "pricemix/?min=" + minn + "&max=" + maxx + "&");
		model.addAttribute("scp", "pricemix/?min=" + minn + "&max=" + maxx + "&" + "p=" + (pn + 1));
		model.addAttribute("scpt", "pricemix/?min=" + minn + "&max=" + maxx + "&" + "p=" + (pn - 1));
		model.addAttribute("sid", "?min=" + minn + "&max=" + maxx + "&");
		model.addAttribute("spg", "pricemix?");
		model.addAttribute("page", page);
		return "user/product/product";
	}

	////////////////////////////////////////////////////

	@RequestMapping("product/detail/{id}")
	public String detail(Model model, @PathVariable("id") Long id) {
		Products item = productsService.findById(id);
		model.addAttribute("item", item);

		String cid = item.getCategories().getId().toString();
		List<Products> items = productsDAO.findByCategoryId(cid);
		model.addAttribute("items", items);

		List<Reviews> reviews = reviewsDAO.findByProductId(id);
		model.addAttribute("reviews", reviews);

		return "user/product/product-detail";
	}

	@Autowired
	OrderDetailService orderDetailService;

	@RequestMapping("product/feedback/{id}")
	public String feedback(Model model, @PathVariable("id") Long id) {
		Order_detail item = orderDetailService.findById(id);
		model.addAttribute("item", item);
		return "user/product/feedback";
	}

	@Autowired
	ServletContext app;

	@PostMapping("review/push/{id}")
	public String review(Model model, @PathVariable("id") Long id, @RequestParam("comment") String comment,
			@RequestParam("image") MultipartFile image) throws IllegalStateException, IOException {

		Reviews reviews = new Reviews();
		if (!image.isEmpty()) {
			String filename = image.getOriginalFilename();
			File file = new File(app.getRealPath("/images/" + filename));
			image.transferTo(file);
			reviews.setImage(filename);
		}
		
		reviews.setComment(comment);
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		reviews.setAccounts(accountsDAO.getById(username));
		reviews.setCreate_date(new Date());
		reviews.setProducts(productsDAO.getById(id));
		reviewsDAO.save(reviews);
		return "redirect:/home/product/detail/" + id;
	}

}
