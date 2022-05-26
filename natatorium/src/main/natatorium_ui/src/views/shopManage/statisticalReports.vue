<template>
  <div class="statistical">
    <operate-panel :showDefault="false" buttonName="生成统计" @search="getStatistic">
      <template #input>
        <el-date-picker
            v-model="dateRange"
            type="monthrange"
            align="right"
            unlink-panels
            range-separator="~"
            value-format="yyyy-MM-dd"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
            :picker-options="pickerOptions">
        </el-date-picker>
      </template>
    </operate-panel>
    <div class="reports">
      <el-row :gutter="20">
        <el-col :span="15">
          <el-row class="dataIntro" :gutter="10">
            <el-col :span="12">
            <el-card class="box-card member">
              <div class="card-title">会员总数:</div>
              <transition>
                <div class="text item">
                  {{ members.toFixed(0) }}
                </div>
              </transition>

            </el-card>
            </el-col>
            <el-col :span="12">
            <el-card class="box-card business">
              <div class="card-title">业务数量:</div>
              <div class="text item">
                {{business.toFixed(0)}}
              </div>
            </el-card>
            </el-col>
          </el-row>
          <el-row>
          <el-card class="box-card line" :body-style="{ padding: '10px' }">
            <div id="lineChart" style="width: 100%;height: 420px"></div>
          </el-card>
          </el-row>
        </el-col>
        <el-col :span="9">
          <el-card class="box-card pie">
            <div id="pieChart" style="width: 100%;height: 500px"></div>
          </el-card>
        </el-col>
      </el-row>


    </div>
  </div>
</template>

<script>
import OperatePanel from "components/operatePanel";
import * as echarts from "echarts"
import {statistic} from "../../network/requestAPI/shopManage/shop";
import gsap from 'gsap'
export default {
  name: "statisticalReports",
  components: {OperatePanel},
  data(){
    return{
      pickerOptions: {
        shortcuts: [ {
          text: '过去一年',
          onClick(picker) {
            const end = new Date();
            const start = new Date();
            end.setTime(start.getTime() - 3600 * 1000 * 24 * 365);
            picker.$emit('pick', [start, end]);
          }
        }, {
          text: '过去六个月',
          onClick(picker) {
            const end = new Date();
            const start = new Date();
            start.setMonth(start.getMonth() - 6);
            picker.$emit('pick', [start, end]);
          }
        }],
        disabledDate:(currentDate)=>{
          return currentDate.getTime() > Date.now()
        }
      },
      dateRange:null,
      members:0,
      business:0,
      lineOption: {
        title: {
          text: '业务增长趋势',
          bottom:'bottom',
          left: 'center'
        },
        tooltip: {
          trigger: 'axis'
        },
        legend: {
          data: []
        },
        grid: {
          left: '2%',
          right: '3%',
          bottom: '8%',
          containLabel: true
        },
        toolbox: {
          feature: {
            saveAsImage: {}
          }
        },
        xAxis: {
          type: 'category',
          boundaryGap: false,
          data: []
        },
        yAxis: {
          type: 'value'
        },
        series: [
          {
            name: '总计',
            type: 'line',
            stack: 'Total',
            data:[]
          }
        ]
      },
      pieOption: {
        legend: {
          top: 'bottom'
        },
        tooltip: {
          trigger: 'item'
        },
        toolbox: {
          show: true,
          feature: {
            mark: { show: true },
            dataView: { show: true, readOnly: false },
            restore: { show: true },
            saveAsImage: { show: true }
          }
        },
        series: [
          {
            name: '业务数量',
            type: 'pie',
            radius: [20, 120],
            center: ['50%', '50%'],
            roseType: 'radius',
            itemStyle: {
              borderRadius: 8
            },
            data: []
          }
        ]
      }
    }
  },
  methods:{
    Mcounter(number){
      gsap.to(this,{duration:2,members:number})
    },
    Bcounter(number){
      gsap.to(this,{duration:1,business:number})
    },
    // searchCharts(){
    //   if(this.dateRange){
    //
    //   }
    // },
    getStatistic(){
      let start
      let end
      if(this.dateRange){
        start = this.dateRange[0]
        end = this.dateRange[1]
      }
      console.log(start)
      let lineChart = echarts.init(document.getElementById("lineChart"))
      let pieChart = echarts.init(document.getElementById("pieChart"))
      statistic({start,end}).then(res => {
        console.log(res)
        //更新前清空数据
        this.lineOption.xAxis.data = []
        this.lineOption.series[0].data = []
        res.line.forEach((item,index) => {
          console.log(item,index)
          this.lineOption.xAxis.data[index] = item.name
          this.lineOption.series[0].data[index] = item.data
        })
        this.lineOption && lineChart.setOption(this.lineOption);
        console.log(this.lineOption)
        this.pieOption.series[0].data = res.pie
        this.pieOption && pieChart.setOption(this.pieOption);
      }).catch(err => {
        console.log(err)
      })
    }
  },
  created() {
    this.Mcounter(1000)
    this.Bcounter(6)
  },
  mounted() {
    this.getStatistic()
  }
}
</script>

<style lang="scss" scoped>
.reports{
  .dataIntro{
    margin-bottom: 10px;
    .box-card{
      .card-title{
        //background-color: red;
        text-align: left;
        font-size: 14px;
      }
      .text{
        font-size: 35px;
      }
      &::v-deep .el-card__body {
        padding: 15px !important;
      }

    }
    .member{
      color: $base-blue;
    }
    .business{
      color: $base-orange;
    }
  }
}
</style>