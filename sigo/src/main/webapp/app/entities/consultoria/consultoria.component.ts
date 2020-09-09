import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IConsultoria } from 'app/shared/model/consultoria.model';
import { ConsultoriaService } from './consultoria.service';
import { ConsultoriaDeleteDialogComponent } from './consultoria-delete-dialog.component';

@Component({
  selector: 'jhi-consultoria',
  templateUrl: './consultoria.component.html',
})
export class ConsultoriaComponent implements OnInit, OnDestroy {
  consultorias?: IConsultoria[];
  eventSubscriber?: Subscription;

  constructor(
    protected consultoriaService: ConsultoriaService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.consultoriaService.query().subscribe((res: HttpResponse<IConsultoria[]>) => (this.consultorias = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInConsultorias();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IConsultoria): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInConsultorias(): void {
    this.eventSubscriber = this.eventManager.subscribe('consultoriaListModification', () => this.loadAll());
  }

  delete(consultoria: IConsultoria): void {
    const modalRef = this.modalService.open(ConsultoriaDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.consultoria = consultoria;
  }
}
