import {ElementRef} from '@angular/core';
import {CurveFactory, curveLinear} from 'd3-shape';
import * as d3 from 'd3';

export interface TimelineSeries {
  data?: TimelineSeriesItem[];
  events?: TimelineEvent[];
}

export interface TimelineSeriesItem {
  value: number;
  date: Date;
}

export interface TimelineEvent {
  name: string;
  type?: string;
  fromDate: Date;
  toDate?: Date;
}

export class Timeline {
  wrapper: any;
  tooltip: any;

  constructor(private elementRef: ElementRef, private curve: CurveFactory = curveLinear, private margin: number = 50) {
  }

  public update(series: TimelineSeries): void {
    if (series && series.data) {
      this.drawChart(series);
    }
  }

  public clear(): void {
    if (this.wrapper) {
      this.wrapper.remove();
    }
    if (this.tooltip) {
      this.tooltip.remove();
    }
  }

  private drawChart(series: TimelineSeries): void {
    const width = this.elementRef.nativeElement.getBoundingClientRect().width;
    const height = this.elementRef.nativeElement.getBoundingClientRect().height;
    if (series.data) {
      this.wrapper = this.createWrapper(width, height);
      const plot = this.createPlotContainer();
      const yScale = this.createYScale(height, series.data);
      const xScale = this.createXScale(width, series.data);
      this.createYAxis(yScale, plot);
      this.createXAxis(height, xScale, plot);
      this.tooltip = this.createTooltip();

      this.createLine(xScale, yScale, plot, series.data);

      if (series.events) {
        this.drawEvents(xScale, yScale, plot, series.events, series.data)
      }
    }
  }

  private createWrapper(width: number, height: number): any {
    return d3.select(this.elementRef.nativeElement)
      .append('svg')
      .attr('height', height)
      .attr('width', width);
  }

  private createTooltip(): any {
    return d3.select(this.elementRef.nativeElement)
      .append('div')
      .style('opacity', 0)
      .style('position', 'absolute')
      .attr('class', 'tooltip')
      .style('background-color', 'white')
      .style('border', 'solid')
      .style('border-width', '1px')
      .style('border-radius', '5px')
      .style('padding', '10px')
  }

  private createXAxis(height: number, xScale: any, plot: any): void {
    const xAxis = plot
      .append('g')
      .attr('id', 'x-axis')
      .style('transform', 'translate(0, ' + (height - 2 * this.margin) + 'px)');

    xAxis.call(d3
      .axisBottom(xScale)
      .ticks(10)
      // @ts-ignore
      .tickFormat(d3.timeFormat('%d / %m')));
  }

  private createYAxis(yScale: any, plot: any): void {
    const yAxis = plot
      .append('g')
      .attr('id', 'y-axis')
      .style('transform', 'translate(' + this.margin + 'px,  0)');

    yAxis.call(d3.axisLeft(yScale));
  }

  private createPlotContainer(): any {
    return this.wrapper
      .append('g')
      .style('transform', 'translate(' + this.margin + 'px, ' + this.margin + 'px)');
  }

  private createYScale(height: number, data: TimelineSeriesItem[]): any {
    return d3
      .scaleLinear()
      // @ts-ignore
      .domain([d3.max(data, d => d.value) + 1, d3.min(data, d => d.value) - 1])
      .range([0, height - 2 * this.margin]);
  }

  private createXScale(width: number, data: TimelineSeriesItem[]): any {
    // @ts-ignore
    return d3.scaleTime().domain(d3.extent(data, d => this.normalizeDate(d.date))).range([this.margin, width - 2 * this.margin]);
  }

  private normalizeDate(date: Date): Date {
    let output = date;
    output.setHours(0);
    output.setMinutes(0);
    return output;
  }

  private createLine(xScale: any, yScale: any, plot: any, data: TimelineSeriesItem[]) {
    const line = d3
      .line()
      .x(d => d[0])
      .y(d => d[1])
      .curve(this.curve);

    const points: [number, number][] = data.map(d => [
      xScale(new Date(d.date)),
      yScale(d.value),
    ]);

    const dataPoints = this.createDataPoints(data, xScale, yScale, plot);

    plot
      .append('g')
      .append('path')
      .attr('id', 'line')
      .style('fill', 'none')
      .style('stroke', 'red')
      .style('stroke-width', '2px')
      .attr('d', line(points));


    this.wrapper.on('mouseover', (event: any) => {
      dataPoints.transition()
        .duration(200)
        .style('opacity', 1);


    })
      .on('mouseleave', () => dataPoints
        .transition()
        .duration(200)
        .style('opacity', 0));

  }

  private createDataPoints(data: TimelineSeriesItem[], xScale: any, yScale: any, plot: any): any {
    return plot.selectAll('dataPoints')
      .data(data)
      .enter()
      .append('circle')
      .attr('fill', 'red')
      .attr('stroke', 'none')
      .attr('cx', function (d: any) {
        return xScale(d.date)
      })
      .attr('cy', function (d: any) {
        return yScale(d.value)
      })
      .attr('r', 3)
      .style('opacity', 0)
      .style('cursor', 'pointer')
      .on('mouseover', (event: any) => {
        this.tooltip.html(Math.round(yScale.invert(event.layerY)))
          .style('top', (event.pageY - 25) + 'px').style('left', (event.pageX - 275) + 'px')
          .transition()
          .duration(200)
          .style('opacity', 1)

      })
      .on('mouseleave', () => this.tooltip
        .transition()
        .duration(200)
        .style('opacity', 0));
  }

  private drawEvents(xScale: any, yScale: any, plot: any, events: TimelineEvent[], data: TimelineSeriesItem[]): void {
    events.forEach(event => this.drawEvent(xScale, yScale, plot, event, data));
  }

  private drawEvent(xScale: any, yScale: any, plot: any, event: TimelineEvent, data: TimelineSeriesItem[]): void {
    const a = plot.append('g');
    a.append('line')
      .style('stroke', 'lightgreen')
      .style('stroke-width', 1)
      .attr('x1', xScale(event.fromDate))
      // @ts-ignore
      .attr('y1', yScale(d3.max(data, d => d.value) + 1))
      .attr('x2', xScale(event.fromDate))
      // @ts-ignore
      .attr('y2', yScale(d3.min(data, d => d.value) - 1));

    a.append('text')
      // @ts-ignore
      .attr('transform', `rotate(90) translate(${yScale(d3.min(data, d => d.value) + 1) / 2},${-xScale(event.fromDate) - 5})`)
      .attr('text-anchor', 'middle')
      .text(event.name);
  }
}
