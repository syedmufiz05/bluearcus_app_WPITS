package com.bluearcus.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.bluearcus.dto.CustomerReportsDto;
import com.bluearcus.dto.CustomerReportsDtoWithCount;
import com.bluearcus.dto.PaymentStatusDto;
import com.bluearcus.exception.CustomMessage;
import com.bluearcus.model.CustomerReports;
import com.bluearcus.model.PostpaidAccounts;
import com.bluearcus.model.PrepaidAccounts;
import com.bluearcus.model.RatingProfileVoucher;
import com.bluearcus.repo.CustomerReportsRepo;
import com.bluearcus.repo.PostpaidAccountsRepo;
import com.bluearcus.repo.PrepaidAccountsRepository;
import com.bluearcus.repo.RatingProfileVoucherRepository;

@Service
public class CustomerReportsServiceImpl implements CustomerReportsService {

	@Autowired
	private CustomerReportsRepo customerReportsRepo;

	@Autowired
	private PrepaidAccountsRepository prepaidAccountsRepository;

	@Autowired
	private PostpaidAccountsRepo postpaidAccountsRepo;
	
	@Autowired
	private RatingProfileVoucherRepository ratingProfileVoucherRepository;

	@Override
	public ResponseEntity saveCustomerReport(CustomerReportsDto customerReportsDto) {
		Optional<CustomerReports> customerReportDb = customerReportsRepo.findByImsi(customerReportsDto.getImsi());
		if (customerReportDb.isPresent()) {

			CustomerReports customerReport = customerReportDb.get();
			customerReport.setPackId(customerReportsDto.getPackId());
			customerReportsRepo.save(customerReport);
			
			if (customerReport.getCustomerType().equalsIgnoreCase("prepaid")) {

				if (customerReportsDto.getPackId() != 0) {
					long packDataBalance = 0;
					long packCallBalance = 0;
					long packSmsBalance = 0;
					
					Optional<RatingProfileVoucher> ratingProfileVoucher = ratingProfileVoucherRepository.findById(customerReportsDto.getPackId());
					RatingProfileVoucher ratingProfileVoucherDb = ratingProfileVoucher.get();
					Integer callBalance = ratingProfileVoucherDb.getCallBalance();
					Integer dataBalance = ratingProfileVoucherDb.getDataBalance();
					Integer smsBalance = ratingProfileVoucherDb.getSmsBalance();
					
					if (dataBalance != 0) {

						if (ratingProfileVoucherDb.getDataBalanceParameter().equalsIgnoreCase("GB")) {
							packDataBalance = convertGigabytesToBytes(dataBalance.longValue());
						}
						if (ratingProfileVoucherDb.getDataBalanceParameter().equalsIgnoreCase("MB")) {
							packDataBalance = convertMegabytesToBytes(dataBalance.longValue());
						}
						if (ratingProfileVoucherDb.getDataBalanceParameter().equalsIgnoreCase("KB")) {
							packDataBalance = convertKilobytesToBytes(dataBalance.longValue());
						}
						
					}
					
					if (callBalance != 0) {
						if (ratingProfileVoucherDb.getCallBalanceParameter().equalsIgnoreCase("Mins")) {
							packCallBalance = convertMinsToSeconds(callBalance.longValue());
						}
					}
					
					packSmsBalance = smsBalance.longValue();
					
					Optional<PrepaidAccounts> prepaidAccount = prepaidAccountsRepository.findByCustomerId(customerReportsDto.getCustomerId());
					
					if (prepaidAccount.isPresent()) {
						PrepaidAccounts prepaidAccountDb = prepaidAccount.get();
						
						prepaidAccountDb.setTotalDataOctetsAvailable(packDataBalance + prepaidAccountDb.getTotalDataOctetsAvailable());
						prepaidAccountDb.setTotalCallSecondsAvailable(packCallBalance + prepaidAccountDb.getTotalCallSecondsAvailable());
						prepaidAccountDb.setTotalSmsAvailable(packSmsBalance + prepaidAccountDb.getTotalSmsAvailable());
						
						prepaidAccountsRepository.save(prepaidAccountDb);
					}

				}

			}
			
			if (customerReport.getCustomerType().equalsIgnoreCase("postpaid")) {
				
				if (customerReport.getPackId() != null) {
					long packDataBalance = 0;
					long packCallBalance = 0;
					long packSmsBalance = 0;
					
					Optional<RatingProfileVoucher> ratingProfileVoucher = ratingProfileVoucherRepository.findById(customerReportsDto.getPackId());
					RatingProfileVoucher ratingProfileVoucherDb = ratingProfileVoucher.get();
					Integer callBalance = ratingProfileVoucherDb.getCallBalance();
					Integer dataBalance = ratingProfileVoucherDb.getDataBalance();
					Integer smsBalance = ratingProfileVoucherDb.getSmsBalance();
					
					if (dataBalance != 0) {

						if (ratingProfileVoucherDb.getDataBalanceParameter().equalsIgnoreCase("GB")) {
							packDataBalance = convertGigabytesToBytes(dataBalance.longValue());
						}
						if (ratingProfileVoucherDb.getDataBalanceParameter().equalsIgnoreCase("MB")) {
							packDataBalance = convertMegabytesToBytes(dataBalance.longValue());
						}
						if (ratingProfileVoucherDb.getDataBalanceParameter().equalsIgnoreCase("KB")) {
							packDataBalance = convertKilobytesToBytes(dataBalance.longValue());
						}
						
					}
					
					if (callBalance != 0) {
						if (ratingProfileVoucherDb.getCallBalanceParameter().equalsIgnoreCase("Mins")) {
							packCallBalance = convertMinsToSeconds(callBalance.longValue());
						}
					}
					
					packSmsBalance = smsBalance.longValue();
					
					Optional<PostpaidAccounts> postpaidAccount = postpaidAccountsRepo.findByCustomerId(customerReportsDto.getCustomerId());
					
					if (postpaidAccount.isPresent()) {
						PostpaidAccounts postpaidAccountDb = postpaidAccount.get();
						
						postpaidAccountDb.setTotalDataOctetsAvailable(packDataBalance + postpaidAccountDb.getTotalDataOctetsAvailable());
						postpaidAccountDb.setTotalCallSecondsAvailable(packCallBalance + postpaidAccountDb.getTotalCallSecondsAvailable());
						postpaidAccountDb.setTotalSmsAvailable(packSmsBalance + postpaidAccountDb.getTotalSmsAvailable());
						
						postpaidAccountsRepo.save(postpaidAccountDb);
					}

				}

			}
			CustomerReportsDto customerReportDtoNew = new CustomerReportsDto(customerReport.getId(),
					customerReport.getFirstName(), customerReport.getLastName(), customerReport.getEkycStatus(),
					customerReport.getEkycToken(), customerReport.getEkycDate(), customerReport.getCustomerId(),
					customerReport.getCustomerType(), customerReport.getImsi(), customerReport.getMsisdn(),
					customerReportsDto.getPackId(), customerReport.getPaymentStatus());
			return new ResponseEntity<>(customerReportDtoNew, HttpStatus.OK);
		}

		CustomerReports customerReport = new CustomerReports();
		customerReport.setFirstName(customerReportsDto.getFirstName());
		customerReport.setLastName(customerReportsDto.getLastName());
		customerReport.setEkycDate(customerReportsDto.getEkycDate());
		customerReport.setEkycStatus(customerReportsDto.getEkycStatus());
		customerReport.setEkycToken(customerReportsDto.getEkycToken());
		customerReport.setCustomerId(customerReportsDto.getCustomerId());
		customerReport.setCustomerType(customerReportsDto.getCustomerType());
		customerReport.setImsi(customerReportsDto.getImsi());
		customerReport.setMsisdn(customerReportsDto.getMsisdn());
		customerReport.setPackId(customerReportsDto.getPackId());
		customerReport.setPaymentStatus(customerReportsDto.getPaymentStatus());
		customerReportsRepo.save(customerReport);

		if (customerReport.getCustomerType().equalsIgnoreCase("prepaid")) {

			PrepaidAccounts prepaidAccounts = new PrepaidAccounts();
			prepaidAccounts.setCustomerId(customerReportsDto.getCustomerId());
			prepaidAccounts.setMsisdn(customerReportsDto.getMsisdn());
			prepaidAccounts.setImsi(customerReportsDto.getImsi());
			prepaidAccounts.setCalledStationId("");
			prepaidAccounts.setMonitoringKey("");
			prepaidAccounts.setAction("");
			prepaidAccounts.setDataParameterType("");
			prepaidAccounts.setCsVoiceCallSeconds(0l);
			prepaidAccounts.setFourGDataOctets(0);
			prepaidAccounts.setFiveGDataOctets(0);
			prepaidAccounts.setVolteCallSeconds(0l);
			prepaidAccounts.setTotalDataOctetsAvailable(0l);
			prepaidAccounts.setTotalInputDataOctetsAvailable(0l);
			prepaidAccounts.setTotalOutputDataOctetsAvailable(0l);
			prepaidAccounts.setTotalDataOctetsConsumed(0l);
			prepaidAccounts.setTotalCallSecondsAvailable(0l);
			prepaidAccounts.setTotalCallSecondsConsumed(0l);
			prepaidAccounts.setTotalSmsAvailable(0l);
			prepaidAccounts.setTotalSmsConsumed(0l);
			prepaidAccountsRepository.save(prepaidAccounts);
		}

		if (customerReport.getCustomerType().equalsIgnoreCase("postpaid")) {

			PostpaidAccounts postpaidAccounts = new PostpaidAccounts();
			postpaidAccounts.setCustomerId(customerReportsDto.getCustomerId());
			postpaidAccounts.setMsisdn(customerReportsDto.getMsisdn());
			postpaidAccounts.setImsi(customerReportsDto.getImsi());
			postpaidAccounts.setDataParameterType("");
			postpaidAccounts.setCsVoiceCallSeconds(0l);
			postpaidAccounts.setFourGDataOctets(0);
			postpaidAccounts.setFiveGDataOctets(0);
			postpaidAccounts.setVolteCallSeconds(0l);
			postpaidAccounts.setTotalDataOctetsAvailable(0l);
			postpaidAccounts.setTotalInputDataOctetsAvailable(0l);
			postpaidAccounts.setTotalOutputDataOctetsAvailable(0l);
			postpaidAccounts.setTotalDataOctetsConsumed(0l);
			postpaidAccounts.setTotalCallSecondsAvailable(0l);
			postpaidAccounts.setTotalCallSecondsConsumed(0l);
			postpaidAccounts.setTotalSmsAvailable(0l);
			postpaidAccounts.setTotalSmsConsumed(0l);
			postpaidAccountsRepo.save(postpaidAccounts);
		}
		CustomerReportsDto customerReportDtoNew = new CustomerReportsDto(customerReport.getId(),
				customerReport.getFirstName(), customerReport.getLastName(), customerReport.getEkycStatus(),
				customerReport.getEkycToken(), customerReport.getEkycDate(), customerReport.getCustomerId(),
				customerReport.getCustomerType(), customerReport.getImsi(), customerReport.getMsisdn(),
				customerReportsDto.getPackId(), customerReport.getPaymentStatus());
		return new ResponseEntity<>(customerReportDtoNew, HttpStatus.OK);
	}

	@Override
	public ResponseEntity editCustomerReport(CustomerReportsDto customerReportsDto) {
		Optional<CustomerReports> customerReportDb = customerReportsRepo.findByCustomerId(customerReportsDto.getCustomerId());
		CustomerReports customerReport = null;
		if (customerReportDb.isPresent()) {
			customerReport = customerReportDb.get();
			customerReport.setCustomerType(customerReportsDto.getCustomerType() != null ? customerReportsDto.getCustomerType() : customerReport.getCustomerType());
			customerReportsRepo.save(customerReport);
			
			if (customerReportsDto.getCustomerType().equalsIgnoreCase("prepaid")) {
				
				// Delete customer record into the postpaid account
				Optional<PostpaidAccounts> postpaidAccount = postpaidAccountsRepo.findByCustomerId(customerReportsDto.getCustomerId());
				postpaidAccountsRepo.delete(postpaidAccount.get());
				
				PrepaidAccounts prepaidAccounts = new PrepaidAccounts();
				prepaidAccounts.setCustomerId(customerReportsDto.getCustomerId());
				prepaidAccounts.setMsisdn(customerReport.getMsisdn());
				prepaidAccounts.setImsi(customerReport.getImsi());
				prepaidAccounts.setCalledStationId("");
				prepaidAccounts.setMonitoringKey("");
				prepaidAccounts.setAction("");
				prepaidAccounts.setDataParameterType("");
				prepaidAccounts.setCsVoiceCallSeconds(0l);
				prepaidAccounts.setFourGDataOctets(0);
				prepaidAccounts.setFiveGDataOctets(0);
				prepaidAccounts.setVolteCallSeconds(0l);
				prepaidAccounts.setTotalDataOctetsAvailable(0l);
				prepaidAccounts.setTotalInputDataOctetsAvailable(0l);
				prepaidAccounts.setTotalOutputDataOctetsAvailable(0l);
				prepaidAccounts.setTotalDataOctetsConsumed(0l);
				prepaidAccounts.setTotalCallSecondsAvailable(0l);
				prepaidAccounts.setTotalCallSecondsConsumed(0l);
				prepaidAccounts.setTotalSmsAvailable(0l);
				prepaidAccounts.setTotalSmsConsumed(0l);
				prepaidAccountsRepository.save(prepaidAccounts);
			}
			else {
				
				//delete customer record into the prepaid account
				Optional<PrepaidAccounts> prepaidAccounts = prepaidAccountsRepository.findByCustomerId(customerReportsDto.getCustomerId());
				prepaidAccountsRepository.delete(prepaidAccounts.get());
				
				PostpaidAccounts postpaidAccounts = new PostpaidAccounts();
				postpaidAccounts.setCustomerId(customerReportsDto.getCustomerId());
				postpaidAccounts.setMsisdn(customerReport.getMsisdn());
				postpaidAccounts.setImsi(customerReport.getImsi());
				postpaidAccounts.setDataParameterType("");
				postpaidAccounts.setCsVoiceCallSeconds(0l);
				postpaidAccounts.setFourGDataOctets(0);
				postpaidAccounts.setFiveGDataOctets(0);
				postpaidAccounts.setVolteCallSeconds(0l);
				postpaidAccounts.setTotalDataOctetsAvailable(0l);
				postpaidAccounts.setTotalInputDataOctetsAvailable(0l);
				postpaidAccounts.setTotalOutputDataOctetsAvailable(0l);
				postpaidAccounts.setTotalDataOctetsConsumed(0l);
				postpaidAccounts.setTotalCallSecondsAvailable(0l);
				postpaidAccounts.setTotalCallSecondsConsumed(0l);
				postpaidAccounts.setTotalSmsAvailable(0l);
				postpaidAccounts.setTotalSmsConsumed(0l);
				postpaidAccountsRepo.save(postpaidAccounts);
			}
			
			CustomerReportsDto customerReportsDtoNew = new CustomerReportsDto(customerReport.getId(),
					customerReport.getFirstName(), customerReport.getLastName(), customerReport.getEkycStatus(),
					customerReport.getEkycToken(), customerReport.getEkycDate(), customerReport.getCustomerId(),
					customerReport.getCustomerType(), customerReport.getImsi(), customerReport.getMsisdn(),
					customerReport.getPackId(), customerReport.getPaymentStatus());
			return new ResponseEntity<>(customerReportsDtoNew, HttpStatus.OK);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CustomMessage(HttpStatus.NOT_FOUND.value(), "Customer Id does n't exist"));
	}

	@Override
	public ResponseEntity updatePaymentStatus(PaymentStatusDto paymentStatusDto) {
		Optional<CustomerReports> customerReportDb = customerReportsRepo.findByCustomerId(paymentStatusDto.getCustomerId());
		if (customerReportDb.isPresent()) {
			CustomerReports customerReport = customerReportDb.get();
			customerReport.setPaymentStatus(paymentStatusDto.getPaymentStatus());
			customerReportsRepo.save(customerReport);
			CustomerReportsDto customerReportDtoNew = new CustomerReportsDto(customerReport.getId(),
					customerReport.getFirstName(), customerReport.getLastName(), customerReport.getEkycStatus(),
					customerReport.getEkycToken(), customerReport.getEkycDate(), customerReport.getCustomerId(),
					customerReport.getCustomerType(), customerReport.getImsi(), customerReport.getMsisdn(),
					customerReport.getPackId(), customerReport.getPaymentStatus());
			return new ResponseEntity<>(customerReportDtoNew, HttpStatus.OK);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CustomMessage(HttpStatus.NOT_FOUND.value(), "Customer Id does n't exist"));
	}

	@Override
	public ResponseEntity deleteCustomerReport(Integer customerId) {
		Optional<CustomerReports> customerReportDb = customerReportsRepo.findByCustomerId(customerId);
		if (customerReportDb.isPresent()) {
			CustomerReports customerReport = customerReportDb.get();
			if (customerReport.getCustomerType().equalsIgnoreCase("prepaid")) {
				Optional<PrepaidAccounts> prepaidAccounts = prepaidAccountsRepository.findByCustomerId(customerId);
				prepaidAccountsRepository.delete(prepaidAccounts.get());
			} else {
				Optional<PostpaidAccounts> postpaidAccount = postpaidAccountsRepo.findByCustomerId(customerId);
				postpaidAccountsRepo.delete(postpaidAccount.get());
			}
			customerReportsRepo.deleteByCustomerId(customerId);
			return ResponseEntity.status(HttpStatus.OK).body(new CustomMessage(HttpStatus.OK.value(), "Deleted successfully"));
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CustomMessage(HttpStatus.NOT_FOUND.value(), "Invalid customer id"));
	}

	@Override
	public CustomerReportsDtoWithCount getAllCustomerReports() {
		List<CustomerReports> customerReports = customerReportsRepo.findAll();
		List<CustomerReportsDto> customerReportList = new ArrayList<>();
		int customerCount = 0;
		for (CustomerReports customerReport : customerReports) {
			CustomerReportsDto customerReportsDto = new CustomerReportsDto();
			customerReportsDto.setId(customerReport.getId());
			customerReportsDto.setFirstName(customerReport.getFirstName());
			customerReportsDto.setLastName(customerReport.getLastName());
			customerReportsDto.setEkycStatus(customerReport.getEkycStatus());
			customerReportsDto.setEkycToken(customerReport.getEkycToken());
			customerReportsDto.setEkycDate(customerReport.getEkycDate());
			customerReportsDto.setCustomerId(customerReport.getCustomerId());
			customerReportsDto.setCustomerType(customerReport.getCustomerType());
			customerReportsDto.setImsi(customerReport.getImsi());
			customerReportsDto.setMsisdn(customerReport.getMsisdn());
			customerReportsDto.setPackId(customerReport.getPackId());
			customerReportsDto.setPaymentStatus(customerReport.getPaymentStatus());
			customerReportList.add(customerReportsDto);
			customerCount = customerCount + 1;
		}
		CustomerReportsDtoWithCount customerReportsDtoWithCount = new CustomerReportsDtoWithCount(customerReportList,customerCount);
		return customerReportsDtoWithCount;
	}

	@Override
	public CustomerReportsDtoWithCount getAllPostpaidCustomerReports() {
		List<CustomerReports> customerReports = customerReportsRepo.findAll();
		List<CustomerReportsDto> customerReportList = new ArrayList<>();
		int customerCount = 0;
		for (CustomerReports customerReport : customerReports) {
			
			if (customerReport.getCustomerType().equalsIgnoreCase("postpaid")) {
				CustomerReportsDto customerReportsDto = new CustomerReportsDto();
				customerReportsDto.setId(customerReport.getId());
				customerReportsDto.setFirstName(customerReport.getFirstName());
				customerReportsDto.setLastName(customerReport.getLastName());
				customerReportsDto.setEkycStatus(customerReport.getEkycStatus());
				customerReportsDto.setEkycToken(customerReport.getEkycToken());
				customerReportsDto.setEkycDate(customerReport.getEkycDate());
				customerReportsDto.setCustomerId(customerReport.getCustomerId());
				customerReportsDto.setCustomerType(customerReport.getCustomerType());
				customerReportsDto.setImsi(customerReport.getImsi());
				customerReportsDto.setMsisdn(customerReport.getMsisdn());
				customerReportsDto.setPackId(customerReport.getPackId());
				customerReportsDto.setPaymentStatus(customerReport.getPaymentStatus());
				customerReportList.add(customerReportsDto);
				customerCount = customerCount + 1;
			}

		}
		CustomerReportsDtoWithCount customerReportsDtoWithCount = new CustomerReportsDtoWithCount(customerReportList,customerCount);
		return customerReportsDtoWithCount;
	}

	@Override
	public CustomerReportsDtoWithCount getAllPrepaidCustomerReports() {
		List<CustomerReports> customerReports = customerReportsRepo.findAll();
		List<CustomerReportsDto> customerReportList = new ArrayList<>();
		int customerCount = 0;
		for (CustomerReports customerReport : customerReports) {
			
			if (customerReport.getCustomerType().equalsIgnoreCase("prepaid")) {
				CustomerReportsDto customerReportsDto = new CustomerReportsDto();
				customerReportsDto.setId(customerReport.getId());
				customerReportsDto.setFirstName(customerReport.getFirstName());
				customerReportsDto.setLastName(customerReport.getLastName());
				customerReportsDto.setEkycStatus(customerReport.getEkycStatus());
				customerReportsDto.setEkycToken(customerReport.getEkycToken());
				customerReportsDto.setEkycDate(customerReport.getEkycDate());
				customerReportsDto.setCustomerId(customerReport.getCustomerId());
				customerReportsDto.setCustomerType(customerReport.getCustomerType());
				customerReportsDto.setImsi(customerReport.getImsi());
				customerReportsDto.setMsisdn(customerReport.getMsisdn());
				customerReportsDto.setPackId(customerReport.getPackId());
				customerReportsDto.setPaymentStatus(customerReport.getPaymentStatus());
				customerReportList.add(customerReportsDto);
				customerCount = customerCount + 1;
			}

		}
		CustomerReportsDtoWithCount customerReportsDtoWithCount = new CustomerReportsDtoWithCount(customerReportList,customerCount);
		return customerReportsDtoWithCount;
	}

	@Override
	public CustomerReportsDtoWithCount getAllActiveCustomerReports() {
		List<CustomerReports> customerReports = customerReportsRepo.findAll();
		List<CustomerReportsDto> customerReportList = new ArrayList<>();
		int customerCount = 0;
		for (CustomerReports customerReport : customerReports) {
			
			if (customerReport.getEkycStatus().equalsIgnoreCase("active")) {
				CustomerReportsDto customerReportsDto = new CustomerReportsDto();
				customerReportsDto.setId(customerReport.getId());
				customerReportsDto.setFirstName(customerReport.getFirstName());
				customerReportsDto.setLastName(customerReport.getLastName());
				customerReportsDto.setEkycStatus(customerReport.getEkycStatus());
				customerReportsDto.setEkycToken(customerReport.getEkycToken());
				customerReportsDto.setEkycDate(customerReport.getEkycDate());
				customerReportsDto.setCustomerId(customerReport.getCustomerId());
				customerReportsDto.setCustomerType(customerReport.getCustomerType());
				customerReportsDto.setImsi(customerReport.getImsi());
				customerReportsDto.setMsisdn(customerReport.getMsisdn());
				customerReportsDto.setPackId(customerReport.getPackId());
				customerReportsDto.setPaymentStatus(customerReport.getPaymentStatus());
				customerReportList.add(customerReportsDto);
				customerCount = customerCount + 1;
			}

		}
		CustomerReportsDtoWithCount customerReportsDtoWithCount = new CustomerReportsDtoWithCount(customerReportList,customerCount);
		return customerReportsDtoWithCount;
	}

	@Override
	public CustomerReportsDtoWithCount getAllInactiveCustomerReports() {
		List<CustomerReports> customerReports = customerReportsRepo.findAll();
		List<CustomerReportsDto> customerReportList = new ArrayList<>();
		int customerCount = 0;
		for (CustomerReports customerReport : customerReports) {
			
			if (customerReport.getEkycStatus().equalsIgnoreCase("Inactive")) {
				CustomerReportsDto customerReportsDto = new CustomerReportsDto();
				customerReportsDto.setId(customerReport.getId());
				customerReportsDto.setFirstName(customerReport.getFirstName());
				customerReportsDto.setLastName(customerReport.getLastName());
				customerReportsDto.setEkycStatus(customerReport.getEkycStatus());
				customerReportsDto.setEkycToken(customerReport.getEkycToken());
				customerReportsDto.setEkycDate(customerReport.getEkycDate());
				customerReportsDto.setCustomerId(customerReport.getCustomerId());
				customerReportsDto.setCustomerType(customerReport.getCustomerType());
				customerReportsDto.setImsi(customerReport.getImsi());
				customerReportsDto.setMsisdn(customerReport.getMsisdn());
				customerReportsDto.setPackId(customerReport.getPackId());
				customerReportsDto.setPaymentStatus(customerReport.getPaymentStatus());
				customerReportList.add(customerReportsDto);
				customerCount = customerCount + 1;
			}

		}
		CustomerReportsDtoWithCount customerReportsDtoWithCount = new CustomerReportsDtoWithCount(customerReportList,customerCount);
		return customerReportsDtoWithCount;
	}

	@Override
	public List<CustomerReportsDto> searchCustomerRecords(String keyword) {
		List<CustomerReports> customerReports = customerReportsRepo.searchReportsByName(keyword);
		List<CustomerReportsDto> customerReportList = new ArrayList<>();
		for (CustomerReports customerReport : customerReports) {
			CustomerReportsDto customerReportsDto = new CustomerReportsDto();
			customerReportsDto.setId(customerReport.getId());
			customerReportsDto.setFirstName(customerReport.getFirstName());
			customerReportsDto.setLastName(customerReport.getLastName());
			customerReportsDto.setEkycStatus(customerReport.getEkycStatus());
			customerReportsDto.setEkycToken(customerReport.getEkycToken());
			customerReportsDto.setEkycDate(customerReport.getEkycDate());
			customerReportsDto.setCustomerId(customerReport.getCustomerId());
			customerReportsDto.setCustomerType(customerReport.getCustomerType());
			customerReportsDto.setImsi(customerReport.getImsi());
			customerReportsDto.setMsisdn(customerReport.getMsisdn());
			customerReportsDto.setPackId(customerReport.getPackId());
			customerReportsDto.setPaymentStatus(customerReport.getPaymentStatus());
			customerReportList.add(customerReportsDto);
		}
		return customerReportList;
	}
	
	public static long convertGigabytesToBytes(Long gigaBytes) {
		// 1 GB = 1024^3 bytes
		BigDecimal gigaBytesBigDecimal = new BigDecimal(String.valueOf(gigaBytes));
		BigDecimal bytesBigDecimal = gigaBytesBigDecimal.multiply(BigDecimal.valueOf(Math.pow(1024, 3)));
		return bytesBigDecimal.longValue();
	}

	public static long convertMegabytesToBytes(Long megaBytes) {
		// 1 MB = 1024^2 bytes
		BigDecimal megaBytesBigDecimal = new BigDecimal(String.valueOf(megaBytes));
		BigDecimal bytesBigDecimal = megaBytesBigDecimal.multiply(BigDecimal.valueOf(Math.pow(1024, 2)));
		return bytesBigDecimal.longValue();
	}

	public static long convertKilobytesToBytes(Long kiloBytes) {
		// 1 KB = 1024^1 bytes
		BigDecimal kiloBytesBigDecimal = new BigDecimal(String.valueOf(kiloBytes));
		BigDecimal bytesBigDecimal = kiloBytesBigDecimal.multiply(BigDecimal.valueOf(Math.pow(1024, 1)));
		return bytesBigDecimal.longValue();
	}
	
	public static long convertMinsToSeconds(Long mins) {
		// 1 Min = 60^1 seconds
		BigDecimal minsBigDecimal = new BigDecimal(String.valueOf(mins));
		BigDecimal secondsBigDecimal = minsBigDecimal.multiply(BigDecimal.valueOf(Math.pow(60, 1)));
		return secondsBigDecimal.longValue();
	}
	
}
