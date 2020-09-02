import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ICompra } from 'app/shared/model/compra.model';
import { CompraService } from './compra.service';
import { CompraDeleteDialogComponent } from './compra-delete-dialog.component';

@Component({
  selector: 'jhi-compra',
  templateUrl: './compra.component.html',
})
export class CompraComponent implements OnInit, OnDestroy {
  compras?: ICompra[];
  eventSubscriber?: Subscription;

  constructor(protected compraService: CompraService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.compraService.query().subscribe((res: HttpResponse<ICompra[]>) => (this.compras = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInCompras();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ICompra): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInCompras(): void {
    this.eventSubscriber = this.eventManager.subscribe('compraListModification', () => this.loadAll());
  }

  delete(compra: ICompra): void {
    const modalRef = this.modalService.open(CompraDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.compra = compra;
  }
}
