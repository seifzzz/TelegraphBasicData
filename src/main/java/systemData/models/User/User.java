package systemData.models.User;


import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import lombok.experimental.SuperBuilder;

@Entity
@Component
@Table(name="SC_USERS", schema = "MTS_SECURITY")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
@Builder
@Data
public class User implements UserDetails{

	private static final long serialVersionUID = 1L;
	@Id
	private Long USER_ID;
	@Column(name="USER_NAME")
	private String USERNAME;
	private String EMAIL_ADDRESS;
	private String DISPLAY_NAME;
	private String LAST_MODIFIED_BY;
	//	private Long ORG_UNIT_ID;
	@Transient
	private String PASSWORD_ENC;

	//	private String WORKER_ID;
	@Transient
	private String EMP_ORG;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(	name = "SC_USERROLE", schema = "MTS_SECURITY",
			joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "USER_ID"),
			inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "role_id"))
	public Set<Role> ROLES;

	@Transient
	public Set<UserPermission> PERMISSIONS;


	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Set<GrantedAuthority> authorities = new HashSet<>();

		PERMISSIONS.forEach(r -> {
			authorities.add(new SimpleGrantedAuthority(r.getPERMISSION_NAME()));
		});

		return authorities;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public String getPassword() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal instanceof org.springframework.security.core.userdetails.User) {
			return ((org.springframework.security.core.userdetails.User)principal).getPassword();
		}
		else {
			return principal.toString();
		}
	}

	@Override
	public String getUsername() {
		return USERNAME;
	}

	public String getDisplayName() {
		return DISPLAY_NAME;
	}

}
