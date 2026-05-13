package com.campaign.manager.service;

import com.campaign.manager.dto.EmeraldAccountDTO;
import com.campaign.manager.dto.EmeraldBalanceDTO;
import com.campaign.manager.dto.UserDTO;
import com.campaign.manager.exception.DuplicateUsernameException;
import com.campaign.manager.exception.EmeraldAccountAlreadyExistsException;
import com.campaign.manager.model.EmeraldAccount;
import com.campaign.manager.model.User;
import com.campaign.manager.repository.EmeraldAccountRepository;
import com.campaign.manager.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final EmeraldAccountRepository emeraldAccountRepository;

    public UserService(UserRepository userRepository, EmeraldAccountRepository emeraldAccountRepository) {
        this.userRepository = userRepository;
        this.emeraldAccountRepository = emeraldAccountRepository;
    }

    @Transactional(readOnly = true)
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
        return mapToDTO(user);
    }

    @Transactional(readOnly = true)
    public EmeraldBalanceDTO getUserBalance(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));

        if (user.getEmeraldAccount() == null) {
            return new EmeraldBalanceDTO(BigDecimal.ZERO);
        }

        return new EmeraldBalanceDTO(user.getEmeraldAccount().getBalance());
    }

    public UserDTO createUser(String username) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new DuplicateUsernameException("Username '" + username + "' already exists");
        }

        User user = new User(username);
        User savedUser = userRepository.save(user);

        return mapToDTO(savedUser);
    }

    public UserDTO updateUser(Long id, String newUsername) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));

        userRepository.findByUsername(newUsername).ifPresent(existingUser -> {
            if (!existingUser.getId().equals(id)) {
                throw new DuplicateUsernameException("Username '" + newUsername + "' already exists");
            }
        });

        user.setUsername(newUsername);
        User savedUser = userRepository.save(user);

        return mapToDTO(savedUser);
    }

    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new EntityNotFoundException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }

    public EmeraldAccountDTO createEmeraldAccount(Long userId, BigDecimal initialBalance) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));

        if (user.getEmeraldAccount() != null) {
            throw new EmeraldAccountAlreadyExistsException(
                    "User '" + user.getUsername() + "' already has an emerald account"
            );
        }

        if (initialBalance == null || initialBalance.compareTo(BigDecimal.ZERO) < 0) {
            initialBalance = BigDecimal.ZERO;
        }

        EmeraldAccount account = new EmeraldAccount(user, initialBalance);
        EmeraldAccount savedAccount = emeraldAccountRepository.save(account);

        user.setEmeraldAccount(savedAccount);
        userRepository.save(user);

        return mapToAccountDTO(savedAccount);
    }

    @Transactional(readOnly = true)
    public void validateUserHasEmeraldAccount(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));

        if (user.getEmeraldAccount() == null) {
            throw new com.campaign.manager.exception.NoEmeraldAccountException(
                    "User '" + user.getUsername() + "' does not have an emerald account. Please create one first."
            );
        }
    }

    private UserDTO mapToDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());

        if (user.getEmeraldAccount() != null) {
            dto.setHasEmeraldAccount(true);
            dto.setEmeraldBalance(user.getEmeraldAccount().getBalance());
        } else {
            dto.setHasEmeraldAccount(false);
            dto.setEmeraldBalance(null);
        }

        dto.setCampaignCount(user.getCampaigns() != null ? user.getCampaigns().size() : 0);

        return dto;
    }

    private EmeraldAccountDTO mapToAccountDTO(EmeraldAccount account) {
        return new EmeraldAccountDTO(
                account.getId(),
                account.getUser().getId(),
                account.getUser().getUsername(),
                account.getBalance()
        );
    }
}
