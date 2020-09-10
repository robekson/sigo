import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITipoProcesso } from 'app/shared/model/tipo-processo.model';
import { TipoProcessoService } from './tipo-processo.service';
import { TipoProcessoDeleteDialogComponent } from './tipo-processo-delete-dialog.component';

@Component({
  selector: 'jhi-tipo-processo',
  templateUrl: './tipo-processo.component.html',
})
export class TipoProcessoComponent implements OnInit, OnDestroy {
  tipoProcessos?: ITipoProcesso[];
  eventSubscriber?: Subscription;

  constructor(
    protected tipoProcessoService: TipoProcessoService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.tipoProcessoService.query().subscribe((res: HttpResponse<ITipoProcesso[]>) => (this.tipoProcessos = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInTipoProcessos();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ITipoProcesso): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInTipoProcessos(): void {
    this.eventSubscriber = this.eventManager.subscribe('tipoProcessoListModification', () => this.loadAll());
  }

  delete(tipoProcesso: ITipoProcesso): void {
    const modalRef = this.modalService.open(TipoProcessoDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.tipoProcesso = tipoProcesso;
  }
}
