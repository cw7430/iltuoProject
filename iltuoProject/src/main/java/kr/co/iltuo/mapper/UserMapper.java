package kr.co.iltuo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.co.iltuo.dto.UserDTO;

@Mapper
public interface UserMapper {
	List<UserDTO> findAllUsers() throws Exception;
	UserDTO findByUserID(String userID) throws Exception;
	List<UserDTO> findAddress(String userID) throws Exception;
	UserDTO findMainAddress(String userID) throws Exception;
	void updateUserPassword(@Param("userID") String userID, @Param("password") String password) throws Exception;
	int userIDDuplicateCheck(String userID) throws Exception;
	int join(UserDTO user) throws Exception;
	int insertAddress(UserDTO user) throws Exception;
	int updateUserInformation(UserDTO user) throws Exception;
	int setAddressNotMain(UserDTO user) throws Exception;
	int setAddressMain(UserDTO user) throws Exception;
	int deleteAddress(UserDTO user) throws Exception;
}
