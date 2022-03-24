package memer.board.dto;

import java.util.Date;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class MemDto {
	
	@NotNull(message="writer is null.")
	@NotEmpty(message="writer is empty.")
	private String id;
	@NotNull(message="writer is null.")
	private String password;
	private String name;
	@DateTimeFormat(pattern ="yyyyMMdd")
	private Date birth;
	private String address;
	
}