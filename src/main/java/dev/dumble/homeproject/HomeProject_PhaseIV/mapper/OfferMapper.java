package dev.dumble.homeproject.HomeProject_PhaseIV.mapper;

import dev.dumble.homeproject.HomeProject_PhaseIV.dto.OfferDTO;
import dev.dumble.homeproject.HomeProject_PhaseIV.entity.entities.transactions.Offer;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OfferMapper {

	OfferMapper instance = Mappers.getMapper(OfferMapper.class);

	Offer map(OfferDTO offerDTO);

	OfferDTO serialize(Offer offer);

	static OfferMapper getInstance() {
		return instance;
	}
}
