import http from "../http-common"; 

class ComplaintService {
  getAllComplaints(searchDTO) {
    console.log(searchDTO)
    return this.getRequest(`/complaint/complaints`, searchDTO);
  }

  get(complaintId) {
    return this.getRequest(`/complaint/${complaintId}`, null);
  }

  findByField(matchData) {
    return this.getRequest(`/complaint?field=${matchData}`, null);
  }

  addComplaint(data) {
    return http.post("/complaint/addComplaint", data);
  }

  update(data) {
  	return http.post("/complaint/updateComplaint", data);
  }
  
  uploadImage(data,complaintId) {
  	return http.postForm("/complaint/uploadImage/"+complaintId, data);
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

export default new ComplaintService();
