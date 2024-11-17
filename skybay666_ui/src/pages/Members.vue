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
            <member-table
            v-if="members && members.length > 0"
				:title="table1.title"
				:sub-title="table1.subTitle"
				:members="members"
				:totalElements="totalElements"
				:page="page"
				:columns="table1.columns"
 				@get-all-members="getAllMembers"
             >

            </member-table>
          </div>
        </card>
      </div>

    </div>
  </div>
</template>
<script>
import { Card } from "@/components/Card";

import MemberTable from "@/components/MemberTable";
import MemberService from "../services/MemberService";

const tableColumns = [];
const tableData = [
];

export default {
  components: {
    Card,
    MemberTable,
  },
  data() {
    return {
      members: [],
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
    async getAllMembers(sortBy='memberId',sortOrder='true',page="0",size="50") {
      try {
        try {
			const searchDTO = {
				sortBy: sortBy, 
				sortOrder: sortOrder, 
				searchQuery: this.searchQuery,
				page: page, 
				size: size
			};
	        let response = await MemberService.getAllMembers(searchDTO);
	        if (!response.data.empty) {
		        if (response.data.members.length) {
					this.members = response.data.members;
				}
      			this.totalElements = response.data.totalElements;
      			this.page = response.data.page;
	        }
        } catch (error) {
          console.error("Error fetching members:", error);
        }
        
      } catch (error) {
        console.error("Error fetching member details:", error);
      }
    },
  },
  mounted() {
    this.getAllMembers();
  },
  created() {
    this.$root.$on('searchQueryForMembersChanged', (searchQuery) => {
    	this.searchQuery = searchQuery;
    	this.getAllMembers();
    })
  }
};
</script>
<style></style>
