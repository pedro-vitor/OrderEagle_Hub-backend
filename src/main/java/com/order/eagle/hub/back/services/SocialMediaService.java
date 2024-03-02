package com.order.eagle.hub.back.services;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.order.eagle.hub.back.entities.SocialMedia;
import com.order.eagle.hub.back.entities.Store;
import com.order.eagle.hub.back.entities.dto.store.SocialMediaGetDTO;
import com.order.eagle.hub.back.entities.enums.SocialMediaType;
import com.order.eagle.hub.back.repositories.SocialMediaRepository;
import com.order.eagle.hub.back.repositories.StoreRepository;

@Service
public class SocialMediaService {

	@Autowired
	private SocialMediaRepository socialMediaRepository;

	@Autowired
	private StoreService storeService;

	@Autowired
	private StoreRepository storeRepository;

	public List<SocialMedia> findByStore(UUID idStore) {
		var result = socialMediaRepository.findByStore(idStore);
		return result;
	}

	public List<SocialMedia> insertByStore(SocialMediaGetDTO dto, UUID idStore) {
		var store = storeService.findById(idStore);
		this.verifyExistsSocialMedia(store.getSocialMedias(), dto.type());
		var newSocialMedia = this.toSocialMedia(dto, store);
		store.getSocialMedias().add(newSocialMedia);
		store = storeRepository.save(store);
		return store.getSocialMedias();
	}

	public SocialMedia updateByStore(SocialMediaGetDTO dto, UUID idSocialMedia, UUID idStore) {
		if(!socialMediaRepository.existsById(idSocialMedia))
			throw new IllegalArgumentException("Não é possível atualizar esta Social Media pois ela não existe.");
		
		var socialMedias = this.findByStore(idStore);
		this.verifyExistsSocialMediaDiferentSameType(socialMedias, dto.type(), idSocialMedia);
		var socialMediaForUpadate = this.findByIdInListSocialMedia(socialMedias, idSocialMedia);
		this.changeValuesSocialMedias(socialMediaForUpadate, dto);
		return socialMediaRepository.save(socialMediaForUpadate);
	}
	
	public void deleteSocialMedia(UUID idSocialMedia, UUID idStore){
		if(!socialMediaRepository.existsById(idSocialMedia))
			throw new IllegalArgumentException("Erro ao deletar. A rede social não existe.");
		
		var store = storeService.findById(idStore);
		store.getSocialMedias().removeIf(sm -> sm.getId().equals(idSocialMedia));
		
		storeRepository.save(store);
	}

	private SocialMedia toSocialMedia(SocialMediaGetDTO dto, Store store) {
		return new SocialMedia(null, dto.type(), dto.url(), store);
	}

	private void verifyExistsSocialMedia(List<SocialMedia> listSocialMedia, SocialMediaType newType) {
		var result = listSocialMedia.stream().filter(sm -> sm.getType().equals(newType)).collect(Collectors.toList());
		if (!result.isEmpty())
			throw new IllegalArgumentException("Esse tipo de Rede social já existe.");
	}

	private void verifyExistsSocialMediaDiferentSameType(List<SocialMedia> listSocialMedia, SocialMediaType type,
			UUID idSocialMediaForUpdate) {
		for (SocialMedia sm : listSocialMedia) {
			if (sm.getType().equals(type)) {
				if (!sm.getId().equals(idSocialMediaForUpdate)) {
					throw new IllegalArgumentException(
							"Não é possível atualizar esta Rede social pois já existe outra Social Mídia do mesmo tipo.");
				}
			}
		}
	}
	
	private SocialMedia findByIdInListSocialMedia(List<SocialMedia> listSocialMedia, UUID idSocialMedia) {
		var result = listSocialMedia.stream().filter(sm -> sm.getId().equals(idSocialMedia)).findFirst();
		return result.orElseThrow(() -> new IllegalArgumentException("Rede social não encontrada"));
	}

	private void changeValuesSocialMedias(SocialMedia socialMediaForUpdate, SocialMediaGetDTO newDatas) {
		socialMediaForUpdate.setType(newDatas.type());
		socialMediaForUpdate.setUrl(newDatas.url());
	}
}
