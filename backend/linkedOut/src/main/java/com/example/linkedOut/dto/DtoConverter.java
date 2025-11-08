package com.example.linkedOut.dto;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

@Component
public abstract class DtoConverter<E, D> {

    public final D toDto(E entity) {
        if (entity == null) {
            return null;
        }
        
        D dto = createDto();
        mapEntityToDto(entity, dto);
        return dto;
    }
    
    
    public final E toEntity(D dto) {
        if (dto == null) {
            return null;
        }
        
        E entity = createEntity();
        mapDtoToEntity(dto, entity);
        return entity;
    }
   
    
     // Convert list of entities to DTOs
    public final List<D> toDtoList(List<E> entities) {
        if (entities == null) {
            return null;
        }
        return entities.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
    
    
     //Convert list of DTOs to entities
    public final List<E> toEntityList(List<D> dtos) {
        if (dtos == null) {
            return null;
        }
        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
    

    
 // must be implemented by subclasses:
    

    protected abstract D createDto();

    protected abstract E createEntity();
    
    protected abstract void mapEntityToDto(E entity, D dto);
    

    protected abstract void mapDtoToEntity(D dto, E entity);
}
