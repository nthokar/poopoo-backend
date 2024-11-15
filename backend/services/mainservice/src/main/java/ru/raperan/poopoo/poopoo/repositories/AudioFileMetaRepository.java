package ru.raperan.poopoo.poopoo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.raperan.poopoo.poopoo.domain.AudioFileMeta;

import java.util.UUID;

@Repository
public interface AudioFileMetaRepository extends JpaRepository<AudioFileMeta, UUID> {}
