import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IProduto, Produto } from 'app/shared/model/produto.model';
import { ProdutoService } from './produto.service';
import { IMateriaPrima } from 'app/shared/model/materia-prima.model';
import { MateriaPrimaService } from 'app/entities/materia-prima/materia-prima.service';
import { IVenda } from 'app/shared/model/venda.model';
import { VendaService } from 'app/entities/venda/venda.service';

type SelectableEntity = IMateriaPrima | IVenda;

@Component({
  selector: 'jhi-produto-update',
  templateUrl: './produto-update.component.html',
})
export class ProdutoUpdateComponent implements OnInit {
  isSaving = false;
  materiaprimas: IMateriaPrima[] = [];
  vendas: IVenda[] = [];

  editForm = this.fb.group({
    id: [],
    referencia: [null, [Validators.required]],
    nome: [null, [Validators.required]],
    cores: [],
    insumo: [],
    materiaPrimaId: [],
    vendaId: [],
  });

  constructor(
    protected produtoService: ProdutoService,
    protected materiaPrimaService: MateriaPrimaService,
    protected vendaService: VendaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ produto }) => {
      this.updateForm(produto);

      this.materiaPrimaService
        .query({ filter: 'produto-is-null' })
        .pipe(
          map((res: HttpResponse<IMateriaPrima[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IMateriaPrima[]) => {
          if (!produto.materiaPrimaId) {
            this.materiaprimas = resBody;
          } else {
            this.materiaPrimaService
              .find(produto.materiaPrimaId)
              .pipe(
                map((subRes: HttpResponse<IMateriaPrima>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IMateriaPrima[]) => (this.materiaprimas = concatRes));
          }
        });

      this.vendaService.query().subscribe((res: HttpResponse<IVenda[]>) => (this.vendas = res.body || []));
    });
  }

  updateForm(produto: IProduto): void {
    this.editForm.patchValue({
      id: produto.id,
      referencia: produto.referencia,
      nome: produto.nome,
      cores: produto.cores,
      insumo: produto.insumo,
      materiaPrimaId: produto.materiaPrimaId,
      vendaId: produto.vendaId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const produto = this.createFromForm();
    if (produto.id !== undefined) {
      this.subscribeToSaveResponse(this.produtoService.update(produto));
    } else {
      this.subscribeToSaveResponse(this.produtoService.create(produto));
    }
  }

  private createFromForm(): IProduto {
    return {
      ...new Produto(),
      id: this.editForm.get(['id'])!.value,
      referencia: this.editForm.get(['referencia'])!.value,
      nome: this.editForm.get(['nome'])!.value,
      cores: this.editForm.get(['cores'])!.value,
      insumo: this.editForm.get(['insumo'])!.value,
      materiaPrimaId: this.editForm.get(['materiaPrimaId'])!.value,
      vendaId: this.editForm.get(['vendaId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProduto>>): void {
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
}
