package com.selfcoder.user.entity;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_info")
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_info_sequence")
	@SequenceGenerator(name = "user_info_sequence", sequenceName = "user_info_sequence", allocationSize = 1)
	private Long id;

	@Column(nullable = false, name = "email", unique = true)
	@Email(message = "Email should be valid")
	private String email;

	@Column(name = "mobile_number", unique = true)
	@Pattern(regexp = "^[0-9]*$", message = "Mobile number should only contain digits")
	private String mobileNumber;

	@Column(nullable = false, name = "password", length = 255)
	private String password;

	@Column(name = "created_at", updatable = false)
	private Instant createdAt;

	@Column(name = "modified_at")
	private Instant modifiedAt;

	@Column(name = "is_deleted")
	private boolean isDeleted;

	@ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
	@JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
	private List<Role> roles;

	@PrePersist
	protected void onCreate() {
		this.createdAt = Instant.now();
		this.modifiedAt = Instant.now();
	}

	@PreUpdate
	protected void onUpdate() {
		this.modifiedAt = Instant.now();
	}

}
