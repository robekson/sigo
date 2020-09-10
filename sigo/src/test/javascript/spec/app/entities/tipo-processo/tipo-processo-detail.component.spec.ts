import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SigoTestModule } from '../../../test.module';
import { TipoProcessoDetailComponent } from 'app/entities/tipo-processo/tipo-processo-detail.component';
import { TipoProcesso } from 'app/shared/model/tipo-processo.model';

describe('Component Tests', () => {
  describe('TipoProcesso Management Detail Component', () => {
    let comp: TipoProcessoDetailComponent;
    let fixture: ComponentFixture<TipoProcessoDetailComponent>;
    const route = ({ data: of({ tipoProcesso: new TipoProcesso(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SigoTestModule],
        declarations: [TipoProcessoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(TipoProcessoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TipoProcessoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load tipoProcesso on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.tipoProcesso).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
