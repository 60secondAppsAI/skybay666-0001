import http from "../http-common"; 

class MemberService {
  getAllMembers(searchDTO) {
    console.log(searchDTO)
    return this.getRequest(`/member/members`, searchDTO);
  }

  get(memberId) {
    return this.getRequest(`/member/${memberId}`, null);
  }

  findByField(matchData) {
    return this.getRequest(`/member?field=${matchData}`, null);
  }

  addMember(data) {
    return http.post("/member/addMember", data);
  }

  update(data) {
  	return http.post("/member/updateMember", data);
  }
  
  uploadImage(data,memberId) {
  	return http.postForm("/member/uploadImage/"+memberId, data);
  }




	postRequest(url, data) {
		return http.post(url, data);
      };

	getRequest(url, params) {
        return http.get(url, {
        	params: params
        });
    };

}

export default new MemberService();
