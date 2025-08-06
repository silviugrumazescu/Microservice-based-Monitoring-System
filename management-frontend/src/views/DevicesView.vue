<template>
    <div class="row mt-5">
        <h1 class="text-center">Your devices</h1>
    </div>
    <div class="row">
        <div class="d-flex flex-wrap align-items-start justify-content-center m-5">
            <div class="m-5">
                <p class="fw-light fst-italic">Select a device</p>
                <DataTable :value="devices" selectionMode="single" v-model:selection="selectedDevice" scrollable
                    scrollHeight="500px">
                    <Column field="uuid" header="ID"></Column>
                    <Column field="description" header="Description"></Column>
                    <Column field="address" header="Address"></Column>
                    <Column field="maxconsumption" header="Maximum hourly consumption"></Column>
                </DataTable>
            </div>
            <div class="m-5">
                <p class="fw-light fst-italic">Select a date</p>
                <div class="row">
                    <Calendar v-model="graphDate" dateFormat="dd/mm/yy" inline showWeek />
                </div>
                <div class="row">
                    <Button label="Get data" class="mt-3" @click="getDataCurrentDay" />
                </div>

            </div>
            <div class="w-25 m-5">
                <p class="fw-light fst-italic">Visualize data</p>
                <Chart type="bar" :data="chartData" :options="chartOptions" />
            </div>

        </div>
    </div>
</template>

<script>

import DeviceService from '../service/deviceService.js'
import Device from '@/model/Device.js'
import DataTable from 'primevue/datatable';
import Column from 'primevue/column';
import Calendar from 'primevue/calendar'
import Button from 'primevue/button'
import Chart from 'primevue/chart'

import monitorService from '@/service/monitorService'

export default {
    name: 'DevicesView',
    components: {
        DataTable, Column, Button, Calendar, Chart
    },
    beforeMount() {
        this.getUserDevices();

    },
    created() {
        this.initChartData();
    },
    data() {
        return {
            devices: [],
            selectedDevice: new Device('', '', '', ''),
            graphDate: null,
            chartData: null
        }
    },
    methods: {
        getUserDevices() {
            DeviceService.getUserDevices().then(
                response => {
                    this.devices = response.data;
                    console.log(this.devices)
                },
                error => {
                    console.log(error.data);
                }
            )
        },
        getDataCurrentDay() {
            console.log(this.graphDate)
            console.log(this.graphDate.toLocaleDateString('en-US'))
            let date = this.graphDate
            let year = new Intl.DateTimeFormat('en', { year: 'numeric' }).format(date);
            let month = new Intl.DateTimeFormat('en', { month: '2-digit' }).format(date);
            let day = new Intl.DateTimeFormat('en', { day: '2-digit' }).format(date);
            let stringDate = `${day}-${month}-${year}`;
            console.log(stringDate)
            monitorService.getDataCurrentDay(this.selectedDevice.uuid, stringDate).then(
                response => {
                    console.log(response)
                    //this.chartData = this.setChartData()
                    this.formatDataForChart(response.data)
                },
                error => {
                    console.log(error.data)
                }
            )
        },
        initChartData() {
            let labels = []
            let data = Array(24).fill(0)
            for (let i = 0; i <= 23; i++) {
                labels[i] = i + ":00";
            }
            this.chartData = {
                labels: labels,
                datasets: [{
                    label: 'Consumption',
                    data: data,
                    backgroundColor: ['rgba(255, 159, 64, 0.2)', 'rgba(75, 192, 192, 0.2)', 'rgba(54, 162, 235, 0.2)', 'rgba(153, 102, 255, 0.2)'],
                    borderColor: ['rgb(255, 159, 64)', 'rgb(75, 192, 192)', 'rgb(54, 162, 235)', 'rgb(153, 102, 255)'],
                    borderWidth: 1
                }]
            }
        },
        formatDataForChart(info) {

            this.initChartData()

            for (let i = 0; i < info.length; i++) {
                console.log(info[i].timestamp)
                const date = new Date(info[i].timestamp)
                date.setMinutes(new Date().getTimezoneOffset())
                let hour = date.getHours()
                console.log(hour)
                this.chartData.datasets[0].data[hour] = info[i].totalConsumption
            }
            // return {
            //     labels: labels,
            //     datasets: [{
            //         label: 'Consumption',
            //         data: data,
            //         backgroundColor: ['rgba(255, 159, 64, 0.2)', 'rgba(75, 192, 192, 0.2)', 'rgba(54, 162, 235, 0.2)', 'rgba(153, 102, 255, 0.2)'],
            //         borderColor: ['rgb(255, 159, 64)', 'rgb(75, 192, 192)', 'rgb(54, 162, 235)', 'rgb(153, 102, 255)'],
            //         borderWidth: 1
            //     }]
            // }
        }

    }
}

</script>