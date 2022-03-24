package member.board.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import member.board.dto.MemDto;
import member.board.service.MemService;

@Controller
@SessionAttributes("user")
public class MemController {
	@Autowired
	MemService service;
	
	@ModelAttribute("user")
	public MemDto getDto() {
		return new MemDto();
	}
	
	@GetMapping("/joinform")
	public String joinform() {
		return "mem/joinform";
	}
	@GetMapping("/idCheck")
	@ResponseBody
	public String idCheck(String id) {
		String checkid = service.idCheck(id);
		return checkid;
	}
	
	@PutMapping("/insert")
	public String insert(MemDto dto) {
		service.insertMem(dto);
		return "redirect:loginform";
	}
	@GetMapping("/loginform")
	public String loginform() {
		return "mem/loginform";
	}
	@PostMapping("/login")
	public String login(@ModelAttribute("command") @Valid MemDto dto, BindingResult error, Model m) {
		
		MemDto resultDto = service.login(dto);
		if(resultDto == null) {
			error.reject("nocode", "로그인 실패: 아이디나 비밀번호가 틀림");	
			return "mem/loginform";
		}else {//로그인 성공
			m.addAttribute("user", resultDto);
		}
		return "redirect:/main";
	}
	@GetMapping("/logout")
	public String logout(SessionStatus status) {
		status.setComplete();
		return "redirect:/";
	}
	
	@GetMapping("/updateform")
	public String updateform(@ModelAttribute("user") MemDto dto) {
		return "mem/updateform";
	}
	@PostMapping("/update")
	public String update(@ModelAttribute("user") MemDto dto) {
		service.updateMem(dto);
		return "redirect:/main";
	}
	@GetMapping("/deleteform")
	public String deleteform() {
		return "mem/deleteform";
	}
	@PostMapping("/delete")
	public String delete(String formpw, @ModelAttribute("user") MemDto dto, SessionStatus status) {
	
		int i = service.deleteMem(formpw, dto);
		if(i == 0) {
			return "mem/deleteform";
		}else {
			status.setComplete();
			return "redirect:/";
		}
	}
	@RequestMapping("/main")
	public String main(@ModelAttribute("user") MemDto dto) {
		if(dto.getId() != null) {
			return "mem/main";
		}else {
			return "mem/loginform";
		}
	}
}