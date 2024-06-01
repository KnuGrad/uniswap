package com.knu.uniswap.store.domain;

import com.knu.uniswap.common.BaseEntity;
import com.knu.uniswap.member.domain.Member;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Store extends BaseEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    private Member member;

    private String bio;
    private String imageUrl;

    @Builder
    public Store(Long id, Member member, String bio, String imageUrl) {
        this.id = id;
        this.member = member;
        this.bio = bio;
        this.imageUrl = imageUrl;
    }

    public void addProfileImage(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void changeOrAddBio(String bio) {
        this.bio = bio;
    }

}
