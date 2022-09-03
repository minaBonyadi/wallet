package com.leovegas.wallet.api;

import com.leovegas.wallet.dto.PlayerDto;
import com.leovegas.wallet.dto.TransactionDto;
import com.leovegas.wallet.dto.mapper.WalletMapper;
import com.leovegas.wallet.dto.rest_response.RestResponse;
import com.leovegas.wallet.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/wallet")
@RequiredArgsConstructor
public class WalletController {

    private final WalletService walletService;
    private final WalletMapper walletMapper;

//    @ApiOperation(value = "Get player data like name and current balance", response = PlayerDto.class)
    @GetMapping(path = "/player/{playerId}")
    public ResponseEntity<PlayerDto> getPlayerById(@PathVariable long playerId) {
        return new ResponseEntity<>(walletMapper.mapPlayerToPlayerDto(walletService.getPlayerById(playerId)), HttpStatus.OK);
    }

//    @ApiOperation(value = "Create and run a new transaction")
    @PostMapping(path ="/{playerId}/transaction")
    public ResponseEntity<RestResponse> createAndRunTransaction(@PathVariable long playerId, @RequestBody TransactionDto transactionDto) {
        return new ResponseEntity<>(walletService.createAndRunTransaction(playerId, transactionDto), HttpStatus.OK);
    }

//    @ApiOperation(value = "Get transactions per player")
    @GetMapping(path ="/{playerId}/transaction")
    public ResponseEntity<List<TransactionDto>> getPlayerTransactions(@PathVariable long playerId) {
        return new ResponseEntity<>(walletMapper.mapTransactionLstToTransactionDto(
                walletService.getPlayerTransactions(playerId)), HttpStatus.OK);
    }
}
