package com.example.pidev.Service;

import com.example.pidev.Repository.DictionnaireRepository;
import com.example.pidev.entity.DictionnaireBadWords;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BadWordService implements IBadWordService {

@Autowired
DictionnaireRepository dictionnaireRepository;

    @Override
    public List<DictionnaireBadWords> retrieveAllBadWord() {
        return dictionnaireRepository.findAll();
    }

    @Override
    public DictionnaireBadWords addBadWord(DictionnaireBadWords r) {
        return dictionnaireRepository.save(r);
    }

    @Override
    public void updateBadWord(DictionnaireBadWords b, Long idb) {
        DictionnaireBadWords r = dictionnaireRepository.findById(idb).orElse(null);

        r.setId(idb);
        r.setWord(b.getWord());

        dictionnaireRepository.save(r);
    }

    @Override
    public void deleteBadWord(long idB) {
        DictionnaireBadWords r = dictionnaireRepository.findById(idB).orElse(null);

        dictionnaireRepository.delete(r);
    }
}
