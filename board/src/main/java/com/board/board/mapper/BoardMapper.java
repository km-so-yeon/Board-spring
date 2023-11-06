package com.board.board.mapper;

import com.board.entity.Board;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BoardMapper {

    List<Board> selectBoardListByRoleId(@Param("roleId") int roleId);

    boolean selectBoardPermission(@Param("boardId")int boardId, @Param("memberId")int memberId);

}
