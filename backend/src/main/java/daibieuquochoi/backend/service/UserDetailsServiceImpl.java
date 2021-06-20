package daibieuquochoi.backend.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import daibieuquochoi.backend.entity.AccountEntity;
import daibieuquochoi.backend.repository.AccountRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	private AccountRepository accountRepository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String accountName) throws UsernameNotFoundException {
		AccountEntity accountEntity = accountRepository.findByAccountName(accountName)
				.orElseThrow(() -> new UsernameNotFoundException("Tài khoản này không tồn tại trên hệ thống!"));
		return UserDetailsImpl.build(accountEntity);
	}

}
