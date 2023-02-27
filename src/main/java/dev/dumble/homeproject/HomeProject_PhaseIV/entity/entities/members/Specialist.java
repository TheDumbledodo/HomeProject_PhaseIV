package dev.dumble.homeproject.HomeProject_PhaseIV.entity.entities.members;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.dumble.homeproject.HomeProject_PhaseIV.entity.UserEntity;
import dev.dumble.homeproject.HomeProject_PhaseIV.entity.entities.services.Assistance;
import dev.dumble.homeproject.HomeProject_PhaseIV.entity.entities.transactions.Offer;
import dev.dumble.homeproject.HomeProject_PhaseIV.entity.entities.transactions.Review;
import dev.dumble.homeproject.HomeProject_PhaseIV.entity.enums.SpecialistStatus;
import dev.dumble.homeproject.HomeProject_PhaseIV.utils.FileUtils;
import jakarta.persistence.*;
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

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "specialist", fetch = FetchType.EAGER)
	private List<Offer> offers;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "specialist", fetch = FetchType.EAGER)
	private List<Review> reviews;

	private long rating;
	private byte[] profilePicture;

	@Enumerated(value = EnumType.STRING)
	private SpecialistStatus status;

	@Column(length = 16000000)
	public byte[] getData() {
		return profilePicture;
	}

	public void setProfilePicture(MultipartFile file) {
		this.profilePicture = FileUtils.convertImageToBytes(file);
	}

	public void addAssistance(Assistance assistance) {
		this.assistanceList.add(assistance);
		assistance.getSpecialistList().add(this);
	}

	public void removeAssistance(Assistance assistance) {
		this.assistanceList.remove(assistance);
		assistance.getSpecialistList().remove(this);
	}

	public void addRating(int rating) {
		this.rating += rating;
	}

	public void reduceRating(int rating) {
		this.rating -= rating;
	}
}
