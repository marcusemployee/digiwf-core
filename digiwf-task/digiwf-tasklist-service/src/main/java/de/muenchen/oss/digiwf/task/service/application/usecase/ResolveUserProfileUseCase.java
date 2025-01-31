package de.muenchen.oss.digiwf.task.service.application.usecase;

import de.muenchen.oss.digiwf.task.service.application.port.in.ResolveUserProfile;
import de.muenchen.oss.digiwf.task.service.application.port.out.user.UserNotFoundException;
import de.muenchen.oss.digiwf.task.service.application.port.out.user.UserProfilePort;
import de.muenchen.oss.digiwf.task.service.domain.UserProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ResolveUserProfileUseCase implements ResolveUserProfile {

  private final UserProfilePort userProfilePort;

  @Override
  @NonNull
  public UserProfile resolveUserProfile(@NonNull String userId) {
    return userProfilePort.findUser(userId);
  }

  @Override
  @NonNull
  public UserProfile findUserProfile(@NonNull String userId) {
    try {
      return userProfilePort.findUser(userId);
    } catch (UserNotFoundException e) {
      return UserProfile.createUnknownUser(userId);
    }
  }
}
