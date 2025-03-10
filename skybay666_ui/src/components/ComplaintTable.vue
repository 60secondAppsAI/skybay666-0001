
<template>
  <div class="content">
    <!-- search bar -->
    <div class="row my-3 justify-content-end">
      <div class="col-8"></div>
       <div class="col-auto">
        <!-- Header Search Input -->
        <a-input-search class="header-search" :class="searchLoading ? 'loading' : ''" placeholder="Search by BusinessUnit#, Location#, Operator#, City, State, FirstName, LastName…"
          @search="onSearch" :loading='searchLoading' v-model="searchQuery">
          <svg slot="prefix" width="16" height="16" viewBox="0 0 20 20" fill="none" xmlns="http://www.w3.org/2000/svg">
            <path fill-rule="evenodd" clip-rule="evenodd"
              d="M8 4C5.79086 4 4 5.79086 4 8C4 10.2091 5.79086 12 8 12C10.2091 12 12 10.2091 12 8C12 5.79086 10.2091 4 8 4ZM2 8C2 4.68629 4.68629 2 8 2C11.3137 2 14 4.68629 14 8C14 9.29583 13.5892 10.4957 12.8907 11.4765L17.7071 16.2929C18.0976 16.6834 18.0976 17.3166 17.7071 17.7071C17.3166 18.0976 16.6834 18.0976 16.2929 17.7071L11.4765 12.8907C10.4957 13.5892 9.29583 14 8 14C4.68629 14 2 11.3137 2 8Z"
              fill="#111827" />
          </svg>
        </a-input-search>
        <!-- / Header Search Input -->
      </div>
    </div>
    <div class="row">
      <div class="col-12">
        <card>
          <template slot="header">
            <div class="row justify-content-between align-items-between mx-3">

              <h5 class="card-title">Complaints</h5>
              
              <div>
                  <base-button class="btn btn-primary" @click="modalComplaints = true">Add</base-button>
              </div>
              
              <modal :show.sync="modalComplaints">
                <template slot="header">
                  <h5 class="modal-title" id="exampleModalLabel">Add Complaint</h5>
                </template>
                <div>
                  <form @submit.prevent>
  <base-input label="ComplaintId" type="text" placeholder="Enter ComplaintId" v-model="complaintToAdd.complaintId"></base-input>
  <base-input label="Description" type="text" placeholder="Enter Description" v-model="complaintToAdd.description"></base-input>
  <base-input label="SubmittedDate" type="text" placeholder="Enter SubmittedDate" v-model="complaintToAdd.submittedDate"></base-input>
                  </form>
                </div>
                <template slot="footer">
                  <base-button type="primary" @click="handleAddSubmitted()">Save</base-button>
                </template>
              </modal>
            </div>
          </template>


          <!-- Conditionally render the view based on the 'isTableView' flag -->
          <div v-if="isTableView">
            <!-- Render the existing Table View -->
            <a-table :columns="columns" :data-source="complaints" :row-key="record => record.ComplaintId" :pagination="pagination"
              :loading="searchLoading" @change="onTableChange" :scroll="{ x: 'max-content' }">



             <template slot="lastModified" slot-scope="dataIndex">
              	{{ formatTime(dataIndex) }}
              </template>
              <template slot="createdOn" slot-scope="dataIndex">
              	{{ formatTime(dataIndex) }}
              </template>
              <template slot="blackOutStartDate" slot-scope="dataIndex">
              	{{ formatDate(dataIndex) }}
              </template>
            </a-table>
          </div>
          <div v-else>
            <!-- Render the Picture View  -->
            <ComplaintPictureView :complaints="complaints" />
          </div>

        </card>
      </div>
    </div>

  </div>
</template>

<script>
import Modal from "@/components/Modal";
import BaseButton from "@/components/BaseButton";
import BaseInput from "@/components/BaseInput";
import NotificationTemplate from "@/pages/Notifications/NotificationTemplate";
import { Card } from "@/components/Card";
import ComplaintService from "../services/ComplaintService";
import { ASelect, ASelectOption, AButton, Table, Pagination } from "ant-design-vue";
import ComplaintPictureView from './ComplaintPictureView.vue';


const complaintsColumns = [
  "complaintId",
  "year",
  "date",
  "competitionId",
  "complaintId"
]

export default {
  props: {
    complaints: {
      type: Array,
      required: true,
    },
    totalElements: {
      type: Number,
      required: true,
    },
    page: {
      type: Number,
      required: true,
    },
  },
  components: {
    Card,
    Modal,
    BaseButton,
    BaseInput,
    Table,

    Pagination,
    ComplaintPictureView
  },

  data() {
    return {
      modalComplaints: false,
        isTableView: true,

      columns: [
        {
          title: 'Complaint Id',
		dataIndex: 'complaintId',
          visible: true,
          scopedSlots: { customRender: 'complaintId' },
          sorter: true
          //sorter: (a, b) => a.complaintId - b.complaintId,
          //sorter: (a, b) => a.complaintId.localeCompare(b.complaintId),
        },
        {
          title: 'Description',
		dataIndex: 'description',
          visible: true,
          scopedSlots: { customRender: 'description' },
          sorter: true
          //sorter: (a, b) => a.description - b.description,
          //sorter: (a, b) => a.description.localeCompare(b.description),
        },
        {
          title: 'Submitted Date',
		dataIndex: 'submittedDate',
          visible: true,
          scopedSlots: { customRender: 'submittedDate' },
          sorter: true
          //sorter: (a, b) => a.submittedDate - b.submittedDate,
          //sorter: (a, b) => a.submittedDate.localeCompare(b.submittedDate),
        },
      ],
      pagination: {
        current: 1,
        pageSize: 50,
        total: 0,
        showSizeChanger: true,
        showQuickJumper: true,
        showTotal: (total) => `Total ${total} complaints`,
      },

      complaints: [],
      complaintToAdd: {},

      complaintsTable: {
        columns: [...complaintsColumns],
        data: [],
      },

      // New properties for sorting and searching
      sortBy: 'complaintId',           // Column to sort by
      sortOrder: 'asc',     // Sort order (asc or desc)
      searchQuery: '',      // Search query
      searchLoading: false, // Initialize searchLoading property


    };
  },
  watch: {
    searchQuery: {
      handler: "handleSearchQueryChanged", // Call the fetchData method when searchQuery changes
      immediate: true, // Trigger immediately when the component is mounted
    },
  },

  methods: {

    async renderComplaintsTable() {
      this.searchLoading = true; // Set searchLoading to true while fetching data
      this.searchLoading = false;

      this.pagination.total = this.totalElements;
      this.pagination.current = this.page;

      let complaintsTableData = [];
      for (let i = 0; i < this.complaints.length; i++) {
        complaintsTableData.push({
          id: i,
          complaintId: this.complaints[i].complaintId,
          year: this.complaints[i].year,
          date: this.complaints[i].date,
          competitionId: this.complaints[i].competitionId,
          complaintId: this.complaints[i].complaintId,
        });

      }
      this.searchLoading = false;
    },

    async onTableChange(pagination, filters, sorter) {
      if (sorter && sorter.field && sorter.order) {
        this.sortBy = sorter.field;
        if (sorter.order == "ascend") {
            this.sortOrder = "asc";
        } else {
            this.sortOrder = "desc";
        }
      }
      if (pagination) {
        this.pagination.current = pagination.current;
        this.pagination.pageSize = pagination.pageSize;
      }

	  this.$emit('get-all-complaints',this.sortBy,this.sortOrder,this.pagination.current-1,this.pagination.pageSize);
      this.handleTableChanged()
    },
	
	initializeColumns() {
        this.columns = this.columns.filter(item => item.visible);
    },

    routingToFlightDetail(id) {
      this.$router.push({ name: 'FlightDetail', params: { flightId: id.toString() }})
    },
    routingToAirportDetail(id) {
      this.$router.push({ name: 'AirportDetail', params: { airportId: id.toString() }})
    },
    routingToAircraftDetail(id) {
      this.$router.push({ name: 'AircraftDetail', params: { aircraftId: id.toString() }})
    },
    routingToAirlineDetail(id) {
      this.$router.push({ name: 'AirlineDetail', params: { airlineId: id.toString() }})
    },
    routingToPilotDetail(id) {
      this.$router.push({ name: 'PilotDetail', params: { pilotId: id.toString() }})
    },
    routingToCrewDetail(id) {
      this.$router.push({ name: 'CrewDetail', params: { crewId: id.toString() }})
    },
    routingToTicketDetail(id) {
      this.$router.push({ name: 'TicketDetail', params: { ticketId: id.toString() }})
    },
    routingToPassengerDetail(id) {
      this.$router.push({ name: 'PassengerDetail', params: { passengerId: id.toString() }})
    },
    routingToBookingDetail(id) {
      this.$router.push({ name: 'BookingDetail', params: { bookingId: id.toString() }})
    },
    routingToScheduleDetail(id) {
      this.$router.push({ name: 'ScheduleDetail', params: { scheduleId: id.toString() }})
    },
    routingToBaggageDetail(id) {
      this.$router.push({ name: 'BaggageDetail', params: { baggageId: id.toString() }})
    },
    routingToPaymentDetail(id) {
      this.$router.push({ name: 'PaymentDetail', params: { paymentId: id.toString() }})
    },
    routingToLoyaltyProgramDetail(id) {
      this.$router.push({ name: 'LoyaltyProgramDetail', params: { loyaltyProgramId: id.toString() }})
    },
    routingToMemberDetail(id) {
      this.$router.push({ name: 'MemberDetail', params: { memberId: id.toString() }})
    },
    routingToPromotionDetail(id) {
      this.$router.push({ name: 'PromotionDetail', params: { promotionId: id.toString() }})
    },
    routingToComplaintDetail(id) {
      this.$router.push({ name: 'ComplaintDetail', params: { complaintId: id.toString() }})
    },
    routingToFeedbackDetail(id) {
      this.$router.push({ name: 'FeedbackDetail', params: { feedbackId: id.toString() }})
    },
    routingToGateDetail(id) {
      this.$router.push({ name: 'GateDetail', params: { gateId: id.toString() }})
    },
    routingToTerminalDetail(id) {
      this.$router.push({ name: 'TerminalDetail', params: { terminalId: id.toString() }})
    },
    routingToMaintenanceDetail(id) {
      this.$router.push({ name: 'MaintenanceDetail', params: { maintenanceId: id.toString() }})
    },
    
    handleSearchQueryChanged() {
    	console.log("handleSearchQueryChanged CALLED FROM @search")
    	this.$root.$emit('searchQueryForComplaintsChanged', this.searchQuery);
		//this.renderComplaintsTable();
    },

    onSearch(value) {
      console.log(value);
      this.searchQuery = value; // Update searchQuery when the user types
    },

    toggleView() {
      this.isTableView = !this.isTableView;
    },
	
	async handleAddSubmitted() {
      this.modalComplaints = false;

      const currentDate = new Date().getTime();
      this.complaintToAdd.created = currentDate;

      const jsonData = JSON.stringify(this.complaintToAdd);
      console.log(jsonData);
      
      const res = await ComplaintService.addComplaint(jsonData);

      if (res.status === 200) {
        this.$notify({
          component: NotificationTemplate,
          icon: "tim-icons icon-bell-55",
          type: "success",
          timeout: 3000,
        });
      }
	},
	
	handleTableChanged() {
	},
	
	formatTime(dateString) {
      if(dateString !== null){
        const date = new Date(dateString);
      return `${(date.getMonth() + 1).toString().padStart(2, '0')}-${date.getDate().toString().padStart(2, '0')}-${date.getFullYear()} ${date.getHours().toString().padStart(2, '0')}:${date.getMinutes().toString().padStart(2, '0')} EST`;
      }
      // Format the date here as needed, for example:
    },  
    
 formatDate(dateString) {
    if (dateString !== null) {
	    const date = new Date(dateString);
	    const months = ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'];
	    const day = String(date.getDate()).padStart(2, '0');
	    const monthAbbreviation = months[date.getMonth()];
	    const year = date.getFullYear();
	    return `${day} ${monthAbbreviation} ${year}`;
  	}
  	// Handle the case when dateString is null or invalid
  	return '';
    
   },

  },
  mounted() {
  	this.initializeColumns();
    this.renderComplaintsTable();
  }
};
</script>

<style>
.modal-dialog {
  margin-top: -300px;
}
.ant-table-row  {
	text-align: center;
}
.ant-table-thead th span {
	text-align: center;
	width: 100%
}
.ant-table-fixed td span {
    text-align: center;
}
.header-search {
  width: 300px !important;
  margin-left: auto !important;
}
</style>
