package com.selfcoder.user.entity;

import java.io.Serializable;
import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "role")
public class Role implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_sequence")
	@SequenceGenerator(name = "role_sequence", sequenceName = "role_sequence", allocationSize = 1)
	private Long id;

	@Column(nullable = false, length = 255, unique = true)
	private String roleName;
	
	@Column(name = "created_at", updatable = false)
	private Instant createdAt;

	@Column(name = "modified_at")
	private Instant modifiedAt;

	@Column(name = "is_deleted")
	private boolean isDeleted;
	
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
