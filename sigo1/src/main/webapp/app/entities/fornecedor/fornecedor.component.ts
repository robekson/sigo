import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IFornecedor } from 'app/shared/model/fornecedor.model';
import { FornecedorService } from './fornecedor.service';
import { FornecedorDeleteDialogComponent } from './fornecedor-delete-dialog.component';

@Component({
  selector: 'jhi-fornecedor',
  templateUrl: './fornecedor.component.html',
})
export class FornecedorComponent implements OnInit, OnDestroy {
  fornecedors?: IFornecedor[];
  eventSubscriber?: Subscription;

  constructor(protected fornecedorService: FornecedorService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.fornecedorService.query().subscribe((res: HttpResponse<IFornecedor[]>) => (this.fornecedors = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInFornecedors();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IFornecedor): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInFornecedors(): void {
    this.eventSubscriber = this.eventManager.subscribe('fornecedorListModification', () => this.loadAll());
  }

  delete(fornecedor: IFornecedor): void {
    const modalRef = this.modalService.open(FornecedorDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.fornecedor = fornecedor;
  }
}
