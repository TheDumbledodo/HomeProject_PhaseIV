package dev.dumble.homeproject.HomeProject_PhaseIV.entity.entities.members;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.dumble.homeproject.HomeProject_PhaseIV.entity.UserEntity;
import dev.dumble.homeproject.HomeProject_PhaseIV.entity.entities.services.Assistance;
import dev.dumble.homeproject.HomeProject_PhaseIV.entity.entities.transactions.Offer;
import dev.dumble.homeproject.HomeProject_PhaseIV.entity.entities.transactions.Review;
import dev.dumble.homeproject.HomeProject_PhaseIV.entity.enums.SpecialistStatus;
import dev.dumble.homeproject.HomeProject_PhaseIV.exception.impl.ImproperProfilePictureException;
import dev.dumble.homeproject.HomeProject_PhaseIV.utils.FileUtils;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

@Entity
@ToString
@DiscriminatorValue(value = "1")
@Getter @Setter @NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@SuperBuilder(setterPrefix = "set")
public class Specialist extends UserEntity {

	@JsonIgnore
	@ManyToMany(cascade = CascadeType.ALL, mappedBy = "specialistList", fetch = FetchType.EAGER)
	private Set<Assistance> assistanceList;

	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "specialist", fetch = FetchType.EAGER)
	private List<Offer> offers;

	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "specialist", fetch = FetchType.EAGER)
	private List<Review> reviews;

	@NotNull @JsonIgnore
	private byte[] profilePicture;

	private long rating;

	@Enumerated(value = EnumType.STRING)
	private SpecialistStatus status;

	@JsonIgnore
	@Column(nullable = false, length = 16000000)
	public byte[] getData() {
		return profilePicture;
	}

	public boolean containsAssistance(Assistance assistance) {
		return this.assistanceList.contains(assistance);
	}

	@Override
	public boolean isEnabled() {
		return this.getStatus() != SpecialistStatus.DISABLED;
	}

	@JsonIgnore
	public boolean isNotAccepted() {
		return this.getStatus() != SpecialistStatus.ACCEPTED;
	}

	public void setProfilePicture(MultipartFile file) {
		var picture = FileUtils.convertImageToBytes(file);
		if (picture == null)
			throw new ImproperProfilePictureException();

		this.profilePicture = picture;
	}

	public void addRating(int rating) {
		this.rating += rating;
	}

	public void reduceRating(int rating) {
		this.rating -= rating;
	}

	public void addAssistance(Assistance assistance) {
		this.assistanceList.add(assistance);
		assistance.getSpecialistList().add(this);
	}

	public void removeAssistance(Assistance assistance) {
		this.assistanceList.remove(assistance);
		assistance.getSpecialistList().remove(this);
	}
}
