<template>
  <div class="content">
    <div class="row">
      <div class="col-12">
        <card class="card-plain">
          <!-- <template slot="header">
            <h4 class="card-title">Table on Plain Background</h4>
            <p class="category">Here is a subtitle for this table</p>
          </template>-->
          <div class="table-full-width text-left">
            <complaint-table
            v-if="complaints && complaints.length > 0"
				:title="table1.title"
				:sub-title="table1.subTitle"
				:complaints="complaints"
				:totalElements="totalElements"
				:page="page"
				:columns="table1.columns"
 				@get-all-complaints="getAllComplaints"
             >

            </complaint-table>
          </div>
        </card>
      </div>

    </div>
  </div>
</template>
<script>
import { Card } from "@/components/Card";

import ComplaintTable from "@/components/ComplaintTable";
import ComplaintService from "../services/ComplaintService";

const tableColumns = [];
const tableData = [
];

export default {
  components: {
    Card,
    ComplaintTable,
  },
  data() {
    return {
      complaints: [],
	  totalElements: 0,
      page: 0,
      searchQuery: '',     
      table1: {
        title: "Simple Table",
        columns: [...tableColumns],
        data: [...tableData],
      },
    };
  },
  methods: {
    async getAllComplaints(sortBy='complaintId',sortOrder='true',page="0",size="50") {
      try {
        try {
			const searchDTO = {
				sortBy: sortBy, 
				sortOrder: sortOrder, 
				searchQuery: this.searchQuery,
				page: page, 
				size: size
			};
	        let response = await ComplaintService.getAllComplaints(searchDTO);
	        if (!response.data.empty) {
		        if (response.data.complaints.length) {
					this.complaints = response.data.complaints;
				}
      			this.totalElements = response.data.totalElements;
      			this.page = response.data.page;
	        }
        } catch (error) {
          console.error("Error fetching complaints:", error);
        }
        
      } catch (error) {
        console.error("Error fetching complaint details:", error);
      }
    },
  },
  mounted() {
    this.getAllComplaints();
  },
  created() {
    this.$root.$on('searchQueryForComplaintsChanged', (searchQuery) => {
    	this.searchQuery = searchQuery;
    	this.getAllComplaints();
    })
  }
};
</script>
<style></style>
