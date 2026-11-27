package com.huumod.ecommerce.file.mapper;

import com.huumod.ecommerce.file.dto.FileInfo;
import com.huumod.ecommerce.file.entity.FileMgmt;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FileMgmtMapper {

    @Mapping(target = "id", source = "name")
    FileMgmt toFileMgmt(FileInfo fileInfo);
}
