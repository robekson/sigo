import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ICompra, Compra } from 'app/shared/model/compra.model';
import { CompraService } from './compra.service';
import { IProduto } from 'app/shared/model/produto.model';
import { ProdutoService } from 'app/entities/produto/produto.service';
import { ICliente } from 'app/shared/model/cliente.model';
import { ClienteService } from 'app/entities/cliente/cliente.service';

type SelectableEntity = IProduto | ICliente;

@Component({
  selector: 'jhi-compra-update',
  templateUrl: './compra-update.component.html',
})
export class CompraUpdateComponent implements OnInit {
  isSaving = false;
  produtos: IProduto[] = [];
  clientes: ICliente[] = [];
  dataDp: any;

  editForm = this.fb.group({
    id: [],
    quantidade: [null, [Validators.required]],
    data: [null, [Validators.required]],
    tamanho: [],
    valor: [],
    produtos: [],
    clienteId: [],
  });

  constructor(
    protected compraService: CompraService,
    protected produtoService: ProdutoService,
    protected clienteService: ClienteService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ compra }) => {
      this.updateForm(compra);

      this.produtoService.query().subscribe((res: HttpResponse<IProduto[]>) => (this.produtos = res.body || []));

      this.clienteService.query().subscribe((res: HttpResponse<ICliente[]>) => (this.clientes = res.body || []));
    });
  }

  updateForm(compra: ICompra): void {
    this.editForm.patchValue({
      id: compra.id,
      quantidade: compra.quantidade,
      data: compra.data,
      tamanho: compra.tamanho,
      valor: compra.valor,
      produtos: compra.produtos,
      clienteId: compra.clienteId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const compra = this.createFromForm();
    if (compra.id !== undefined) {
      this.subscribeToSaveResponse(this.compraService.update(compra));
    } else {
      this.subscribeToSaveResponse(this.compraService.create(compra));
    }
  }

  private createFromForm(): ICompra {
    return {
      ...new Compra(),
      id: this.editForm.get(['id'])!.value,
      quantidade: this.editForm.get(['quantidade'])!.value,
      data: this.editForm.get(['data'])!.value,
      tamanho: this.editForm.get(['tamanho'])!.value,
      valor: this.editForm.get(['valor'])!.value,
      produtos: this.editForm.get(['produtos'])!.value,
      clienteId: this.editForm.get(['clienteId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICompra>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }

  getSelected(selectedVals: IProduto[], option: IProduto): IProduto {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
