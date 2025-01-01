package org.lts.profile_hub.controller;

import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import org.lts.Api;
import org.lts.ProfileHubGrpc;
import org.lts.profile_hub.entity.dto.SetDto;
import org.lts.profile_hub.repository.DiscordUserCacheableRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
public class ProfileGrpcController extends ProfileHubGrpc.ProfileHubImplBase {

    private final DiscordUserCacheableRepository userCacheableRepository;
    private final Logger LOG = LoggerFactory.getLogger(ProfileGrpcController.class);

    @Autowired
    public ProfileGrpcController(DiscordUserCacheableRepository userCacheableRepository) {
        this.userCacheableRepository = userCacheableRepository;
    }

    @Override
    public void addFavourite(Api.FavouriteSet request, StreamObserver<Empty> responseObserver) {
        LOG.debug("New [AddFavourite] request: {}", request);

        SetDto set;
        String discordUserId;
        try {
            set = SetDto.from(request.getSet());
            discordUserId = Objects.requireNonNull(request.getDiscordUserId().getId());
        } catch (IllegalArgumentException | NullPointerException e) {
            LOG.warn("Illegal argument: {}", e.getMessage());
            responseObserver.onError(e);
            return;
        }

        userCacheableRepository.addFavourite(set.toJpa()).forUser(discordUserId);

        responseObserver.onNext(Empty.getDefaultInstance());
        responseObserver.onCompleted();
    }

    @Override
    public void getAllFavourite(Api.DiscordUser request, StreamObserver<Api.FavouriteSets> responseObserver) {

        var discordUserId = request.getId();
        LOG.debug("New [GetAllFavourite] request [{}]: {}", request, discordUserId);

        if (discordUserId == null) {
            responseObserver.onError(new IllegalArgumentException("Discord user id cannot be null"));
            return;
        }

        var favourites = userCacheableRepository.getFavourites(discordUserId);

        responseObserver.onNext(Api.FavouriteSets.newBuilder()
                .addAllSet(favourites != null ? favourites.stream()
                        .map(set -> Api.FavouriteSet.newBuilder()
                                .setSet(Api.Set.newBuilder()
                                        .setNumber(set.getNumber())
                                        .setName(set.getName())
                                        .setYear(set.getYear())
                                        .setThemeId(set.getThemeId())
                                        .setParts(set.getParts())
                                        .setSetImageUrl(set.getSetImageUrl())
                                        .setSetUrl(set.getSetUrl())
                                        .setLastModifiedDate(set.getLastModifiedDate())
                                        .build())
                                .setDiscordUserId(Api.DiscordUser.newBuilder().setId(discordUserId).build())
                                .build())
                        .toList() : List.of())
                .build());

        responseObserver.onCompleted();
    }

}
