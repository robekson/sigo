import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IProcesso } from 'app/shared/model/processo.model';
import { ProcessoService } from './processo.service';
import { ProcessoDeleteDialogComponent } from './processo-delete-dialog.component';

@Component({
  selector: 'jhi-processo',
  templateUrl: './processo.component.html',
})
export class ProcessoComponent implements OnInit, OnDestroy {
  processos?: IProcesso[];
  eventSubscriber?: Subscription;

  constructor(protected processoService: ProcessoService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.processoService.query().subscribe((res: HttpResponse<IProcesso[]>) => (this.processos = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInProcessos();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IProcesso): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInProcessos(): void {
    this.eventSubscriber = this.eventManager.subscribe('processoListModification', () => this.loadAll());
  }

  delete(processo: IProcesso): void {
    const modalRef = this.modalService.open(ProcessoDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.processo = processo;
  }
}
