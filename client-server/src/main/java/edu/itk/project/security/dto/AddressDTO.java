package edu.itk.project.security.dto;


import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class AddressDTO {

	private String street;

	private String city;

	private String state;

	private String country;

	private String zipCode;
}
