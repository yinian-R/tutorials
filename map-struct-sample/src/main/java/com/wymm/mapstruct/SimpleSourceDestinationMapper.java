package com.wymm.mapstruct;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper
public interface SimpleSourceDestinationMapper {
    SimpleDestination simpleSourceToSimpleDestination(SimpleSource source);
    
    SimpleSource simpleDestinationToSimpleSource(SimpleDestination destination);
}
