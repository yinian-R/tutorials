package com.wymm.mapstruct.simple;

import org.mapstruct.Mapper;

@Mapper
public interface SimpleSourceDestinationMapper {
    SimpleDestination simpleSourceToSimpleDestination(SimpleSource source);
    
    SimpleSource simpleDestinationToSimpleSource(SimpleDestination destination);
}
