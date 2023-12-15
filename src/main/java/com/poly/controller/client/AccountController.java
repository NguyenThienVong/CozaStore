package com.poly.controller.client;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.poly.dao.Account_addressDAO;
import com.poly.dao.AccountsDAO;
import com.poly.dao.AuthoritiesDAO;
import com.poly.dao.RolesDAO;
import com.poly.entity.Account_address;
import com.poly.entity.Accounts;
import com.poly.entity.Authorities;
import com.poly.service.Account_addressService;
import com.poly.service.AccountService;
import com.poly.service.impl.MailerServiceImpl;
import com.poly.utils.ParamService;

import net.bytebuddy.utility.RandomString;

@Controller
@RequestMapping("home")
public class AccountController {

	@Autowired
	private PasswordEncoder pe;

	@Autowired
	ParamService paramService;

	@Autowired
	MailerServiceImpl mailer;

	@Autowired
	AccountService accountService;

	@Autowired
	Account_addressService addressService;

	@Autowired
	Account_addressDAO addressDAO;

	@Autowired
	AuthoritiesDAO authoritiesDAO;
	
	@Autowired
	AccountsDAO accountsDAO;
	
	@Autowired
	RolesDAO rolesDAO;

	// Register
	@RequestMapping("register")
	public String register(Model model) {
		Accounts acc = new Accounts();
		model.addAttribute("acc", acc);
		return "user/account/register";
	}

	@RequestMapping("register/create")
	public String save(Model model,@Validated @ModelAttribute("acc") Accounts acc, Errors errors) {
		if(errors.hasErrors()) {			
			return "user/account/register";
		}
		acc.setPhoto("avata.png");
		acc.setPassword(pe.encode(acc.getPassword()));
		acc.setActive(true);
		accountService.create(acc);
		Authorities authorities = new Authorities();
		authorities.setAccounts(accountsDAO.getById(acc.getUsername()));
		authorities.setRoles(rolesDAO.getById("CUST"));
		authoritiesDAO.save(authorities);
		return "forward:/security/login/form";
	}

	// Change Pass
	@GetMapping("change-password")
	public String changepass() {
		return "user/account/change-password";
	}

	@PostMapping("change-password")
	public String change(Model model,
	                     @RequestParam("username") String username,
	                     @RequestParam("password") String password,
	                     @RequestParam("newpassword") String newpassword,
	                     @RequestParam("confirmpassword") String confirmpassword) {
	    try {
	        Accounts accounts = accountService.findById(username);

	        if (!newpassword.isEmpty() && !confirmpassword.isEmpty()) {
	            if (pe.matches(password, accounts.getPassword())) {
	                if (newpassword.equals(confirmpassword)) {
	                    accounts.setPassword(pe.encode(newpassword));
	                    accountService.update(accounts);
	                    model.addAttribute("message", "Đổi mật khẩu xong !");
	                } else {
	                    model.addAttribute("message", "Mật khẩu không trùng nhau !");
	                }
	            } else {
	                model.addAttribute("message", "Sai mật khẩu !");
	            }
	        } else {
	            model.addAttribute("message", "Vui lòng nhập mật khẩu mới và xác nhận mật khẩu !");
	        }
	    } catch (Exception e) {
	        model.addAttribute("message", "Tài khoản không tồn tại !");
	    }
	    return "user/account/change-password";
	}
//	Forgot Pass
	@RequestMapping("forgot-password")
	public String index() {
		return "user/account/forgot-password";
	}

	@PostMapping("forgot-password")
	public String forgot(Model model) {
		String username = paramService.getString("username", "");
		String email = paramService.getString("email", "");
		String subject = "Send your Password !";
		String body = "Your Password : ";
		String randomPassword = RandomString.make(6);

		try {
			Accounts accounts = accountService.findById(username);
			if (!accounts.getEmail().equals(email)) {
				model.addAttribute("message", " Email Wrong !");
			} else {
				accounts.setPassword(pe.encode(randomPassword));
				accountService.update(accounts);
				mailer.send(email, subject, body + randomPassword);
				model.addAttribute("message", "Check Email To See Password !");
			}
		} catch (Exception e) {
			model.addAttribute("message", "Account does not exist !");
		}
		return "user/account/forgot-password";
	}

//	Contact

	@RequestMapping("contact")
	public String contact() {
		return "user/blog/contact";
	}

	@PostMapping("contact")
	public String contact(Model model) {
		String username = paramService.getString("username", "");
		String email = paramService.getString("email", "");
		String subject = "New message (Hỗ Trợ) !";
		String body = "";
		String msg = paramService.getString("msg", "");
		try {
			Accounts accounts = accountService.findById(username);
			if (accounts.getEmail().equals(email)) {
				mailer.send(email, subject, body + "Username : " + username + "<br/>" + "Email : " + accounts.getEmail()
						+ "<br/>" + "Nội Dung : " + msg);
				model.addAttribute("message", "Done !");
			} else {
				model.addAttribute("message", "Wrong Email !");
			}
		} catch (Exception e) {
			model.addAttribute("message", "Account does not exist !");
		}
		return "user/blog/contact";
	}

//	Profile

	@GetMapping("profile/{username}")
	public String profile(@PathVariable("username") String username, Model model) {
		model.addAttribute("profile", accountService.findById(username));
		return "user/account/edit-profile";
	}

	@Autowired
	ServletContext app;

	@PostMapping("profile/edit")
	public String edit(Model model, @RequestParam("username") String username,
			@RequestParam("first_name") String first_name, @RequestParam("last_name") String last_name,
			@RequestParam("email") String email, @RequestParam("phone") String phone,
			@RequestParam("gender") String gender, @RequestParam("birthday") String birthday,
			@RequestParam("nationality") String nationality, @RequestParam("photo") MultipartFile photo)
			throws IllegalStateException, IOException {

		Accounts acc = accountService.findById(username);
		if (!photo.isEmpty()) {
			String filename = photo.getOriginalFilename();
			File file = new File(app.getRealPath("/images/" + filename));
			photo.transferTo(file);
			acc.setPhoto(filename);
		}

		acc.setFirst_name(first_name);
		acc.setLast_name(last_name);
		acc.setEmail(email);
		acc.setGender(Boolean.parseBoolean(gender));
		acc.setBirthday(birthday);
		acc.setNationality(nationality);
		acc.setPhone(phone);

		accountService.update(acc);
		model.addAttribute("profile", acc);
		return "user/account/edit-profile";

	}

//	ADDRESS

	@GetMapping("address/{username}")
	public String address(@PathVariable("username") String username, Model model,
			@ModelAttribute("users") Account_address address) {

		Account_address users = new Account_address();
		model.addAttribute("users", users);

		model.addAttribute("address", addressDAO.findByUsername(username));
		return "user/account/edit-address";
	}

	@RequestMapping("address/{username}/edit/{id}")
	public String detail(Model model, @PathVariable("id") Long id, @PathVariable("username") String username) {
		model.addAttribute("address", addressDAO.findByUsername(username));
		model.addAttribute("users", addressDAO.findById(id).get());
		return "user/account/edit-address";
	}
	
	@RequestMapping("address/{username}/delete/{id}")
	public String delete(Model model, @PathVariable("id") Long id, @PathVariable("username") String username) {
		addressService.delete(id);
		return "redirect:/home/address/"+username;
	}

	@PostMapping("address/save")
	public String save(Model model, @ModelAttribute("users") Account_address address) {
		addressService.update(address);
		return "redirect:/home/address/" + address.getAccounts().getUsername() + "/edit/" + address.getId();
	}

	@RequestMapping("address/create")
	public String create(Model model, @Validated @ModelAttribute("users") Account_address address, Errors errors) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		if (errors.hasErrors()) {
			Accounts user = accountService.findById(username);
			address.setAccounts(user);
			model.addAttribute("address", addressDAO.findByUsername(username));
			return "redirect:/home/address/" + username;
		}	
		Accounts user = accountService.findById(username);
		address.setAccounts(user);
		addressService.create(address);
		model.addAttribute("successMessage", "Tạo địa chỉ thành công");
		return "redirect:/home/address/" + username;
	}
}
