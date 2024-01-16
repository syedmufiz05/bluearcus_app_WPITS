package com.bluearcus.service;

import com.bluearcus.dto.DestinationRatesDto;
import com.bluearcus.model.Destination;
import com.bluearcus.model.DestinationRates;
import com.bluearcus.model.Rates;
import com.bluearcus.repo.DestinationRatesRepository;
import com.bluearcus.repo.DestinationRepository;
import com.bluearcus.repo.RatesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DestinationRatesServiceImpl implements DestinationRatesService {
    @Autowired
    private DestinationRatesRepository destinationRatesRepository;
    @Autowired
    private DestinationRepository destinationRepository;
    @Autowired
    private RatesRepository ratesRepository;

    @Override
    public DestinationRatesDto createDestinationRates(DestinationRatesDto destinationRatesDto) {
        Optional<DestinationRates> destinationRates = destinationRatesRepository.findById(destinationRatesDto.getDestinationRatesId());

        if (destinationRates.isPresent()) {
            DestinationRates destinationRatesDb = destinationRates.get();
            Optional<Destination> destination = destinationRepository.findById(destinationRatesDto.getDestinationId());
            Optional<Rates> rates = ratesRepository.findById(destinationRatesDto.getRatesTag());

            if (destination.isPresent() && rates.isPresent()) {
                Destination destinationDb = destination.get();
                destinationRepository.save(destinationDb);
                Rates ratesDb = rates.get();
                ratesRepository.save(ratesDb);
                destinationRatesDb.setDestination(destinationDb);
                destinationRatesDb.setRates(ratesDb);
                destinationRatesRepository.save(destinationRatesDb);
                return new DestinationRatesDto(destinationRatesDb.getId(), destinationDb.getId(), ratesDb.getId());
            }

            Destination destinationNew = new Destination();
            destinationRepository.save(destinationNew);
            Rates ratesNew = new Rates();
            ratesRepository.save(ratesNew);
            destinationRatesDb.setDestination(destinationNew);
            destinationRatesDb.setRates(ratesNew);
            destinationRatesRepository.save(destinationRatesDb);
            return new DestinationRatesDto(destinationRatesDb.getId(), destinationNew.getId(), ratesNew.getId());
        }

        DestinationRates destinationRatesNew = new DestinationRates();
        Destination destination = new Destination();
        destinationRepository.save(destination);
        Rates rates = new Rates();
        ratesRepository.save(rates);
        destinationRatesNew.setDestination(destination);
        destinationRatesNew.setRates(rates);
        destinationRatesRepository.save(destinationRatesNew);
        return new DestinationRatesDto(destinationRatesNew.getId(), destination.getId(), rates.getId());
    }

    @Override
    public List<DestinationRatesDto> getAllDestinationRates() {
        return destinationRatesRepository.fetchAllDestination();
    }

    @Override
    public String deleteDestinationRates(Integer destinationRatesId) {
        destinationRatesRepository.deleteById(destinationRatesId);
        return "Successfully deleted...";
    }
}
