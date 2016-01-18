package vsp.banks.business.converter;

import vsp.banks.data.dtos.TransferCommitDto;
import vsp.banks.data.values.Transfer;

/**
 * Created by alex on 1/18/16.
 */
public class TransferConverter {

  /**
   * Converts transfer dto to entity.
   * @param dto to convert.
   * @return entity equals to dto.
   */
  public Transfer dtoToEntity(TransferCommitDto dto) {
    String to = dto.getTo();
    String from = dto.getFrom();
    int amount = dto.getAmount();
    String reason = dto.getReason();
    String event = dto.getEvent();
    if (dto.isBankToPlayer()) {
      return Transfer.bankToPlayer(to, amount, reason, event);
    } else if (dto.isPlayerToBank()) {
      return Transfer.playerToBank(from, amount, reason, event);
    } else if (dto.isPlayerToPlayer()) {
      return new Transfer(from, to, amount, reason, event);
    }
    throw new RuntimeException("Conversion error! dto -> entity.");
  }

  /**
   * Converts transfer entity to dto.
   * @param entity to convert.
   * @return dto equals to entity.
   */
  public TransferCommitDto entityToDto(Transfer entity) {
    String to = entity.getTo();
    String from = entity.getFrom();
    int amount = entity.getAmount();
    String reason = entity.getReason();
    String event = entity.getEvent();
    if (entity.isBankToPlayer()) {
      return TransferCommitDto.bankToPlayer(to, amount, reason, event);
    } else if (entity.isPlayerToBank()) {
      return TransferCommitDto.playerToBank(from, amount, reason, event);
    } else if (entity.isPlayerToPlayer()) {
      return new TransferCommitDto(from, to, amount, reason, event);
    }
    throw new RuntimeException("Conversion error! entity -> dto.");
  }
}
